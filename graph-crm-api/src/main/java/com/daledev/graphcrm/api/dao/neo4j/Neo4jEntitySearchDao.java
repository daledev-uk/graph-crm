package com.daledev.graphcrm.api.dao.neo4j;

import com.daledev.graphcrm.api.dao.EntitySearchDao;
import com.daledev.graphcrm.api.dao.neo4j.query.NeoCypherQuery;
import com.daledev.graphcrm.api.dao.neo4j.query.NeoCypherQueryBuilder;
import com.daledev.graphcrm.api.dao.neo4j.query.QueryType;
import com.daledev.graphcrm.api.domain.EntityDefinition;
import com.daledev.graphcrm.api.domain.EntityRelationshipDefinition;
import com.daledev.graphcrm.api.domain.FieldDefinition;
import com.daledev.graphcrm.api.dto.detail.EntityDto;
import com.daledev.graphcrm.api.dto.response.EntitySearchResultsPageDto;
import com.daledev.graphcrm.api.dto.search.EntitySearchRequestDto;
import com.daledev.graphcrm.api.dto.search.SearchCriteriaDto;
import com.daledev.graphcrm.api.dto.search.SearchResultPageSummaryDto;
import com.daledev.graphcrm.api.exception.EntityDefinitionFieldNotFound;
import com.daledev.graphcrm.api.exception.EntityDefinitionNotFound;
import com.daledev.graphcrm.api.repository.EntityDefinitionRepository;
import com.daledev.graphcrm.api.repository.EntityRelationshipDefinitionRepository;
import org.neo4j.ogm.model.Property;
import org.neo4j.ogm.model.Result;
import org.neo4j.ogm.response.model.NodeModel;
import org.neo4j.ogm.session.Session;
import org.springframework.stereotype.Repository;

import java.util.*;

/**
 * @author dale.ellis
 * @since 16/10/2018
 */
@Repository
public class Neo4jEntitySearchDao implements EntitySearchDao {
    public static final String ENTITY_ALIAS = "e";
    private Session session;
    private EntityDefinitionRepository entityDefinitionRepository;
    private EntityRelationshipDefinitionRepository entityRelationshipDefinitionRepository;

    /**
     * @param session
     * @param entityDefinitionRepository
     * @param entityRelationshipDefinitionRepository
     */
    public Neo4jEntitySearchDao(Session session, EntityDefinitionRepository entityDefinitionRepository, EntityRelationshipDefinitionRepository entityRelationshipDefinitionRepository) {
        this.session = session;
        this.entityDefinitionRepository = entityDefinitionRepository;
        this.entityRelationshipDefinitionRepository = entityRelationshipDefinitionRepository;
    }

    @Override
    public EntitySearchResultsPageDto search(EntitySearchRequestDto request) {
        NeoCypherQueryBuilder builder = getBuilderForEntitySearch(request);
        validateCriteria(request);
        createWhereCondition(request.getSearchCriteria(), builder, request.getEntityType());

        builder.returnNodes(ENTITY_ALIAS);
        builder.skipFirstNumberOfResults(request.getStartIndex());
        builder.resultLimit(request.getPageSize());
        NeoCypherQuery cypherQuery = builder.build();

        return executeQuery(cypherQuery, request);
    }

    private void createWhereCondition(SearchCriteriaDto searchCriteria, NeoCypherQueryBuilder builder, String entityType) {
        if (searchCriteria != null) {
            List<SearchCriteriaDto> groupedCriteria = searchCriteria.getGroupedCriteria();
            if (groupedCriteria != null && !groupedCriteria.isEmpty()) {
                createSubConditionCriteria(searchCriteria, builder, entityType);
            } else {
                createSingleCondition(searchCriteria, builder, entityType);
            }
        }
    }

    private void createSubConditionCriteria(SearchCriteriaDto searchCriteria, NeoCypherQueryBuilder builder, String entityType) {
        builder.subCondition(searchCriteria.getLogicalOperator());
        for (SearchCriteriaDto subConditionCriteria : searchCriteria.getGroupedCriteria()) {
            createWhereCondition(subConditionCriteria, builder, entityType);
        }
    }

    private void createSingleCondition(SearchCriteriaDto searchCriteria, NeoCypherQueryBuilder builder, String entityType) {
        AliasAndName relationshipAliasAndAttribute = getRelationshipAliasAndAttribute(searchCriteria, builder, entityType);
        builder.addWhereCondition(relationshipAliasAndAttribute.alias, relationshipAliasAndAttribute.attribute, searchCriteria.getOperator(), searchCriteria.getValue());
    }

    private AliasAndName getRelationshipAliasAndAttribute(SearchCriteriaDto searchCriteria, NeoCypherQueryBuilder builder, String entityType) {
        AliasAndName aliasAndName = new AliasAndName();
        aliasAndName.alias = ENTITY_ALIAS;

        if (entityType == null) {
            aliasAndName.attribute = searchCriteria.getField();
            return aliasAndName;
        }

        String[] fieldParts = searchCriteria.getField().split("\\.");
        Queue<String> fieldPartQueue = new LinkedList<>(Arrays.asList(fieldParts));

        EntityDefinition entityDefinition = getValidatedEntityDefinition(entityType);
        int traversalIndex = 0;

        String fieldName = fieldPartQueue.poll();
        while (fieldName != null) {
            Optional<FieldDefinition> matchedFieldDefinition = entityDefinition.getFieldByName(fieldName);
            final EntityDefinition finalEntityDefinition = entityDefinition;
            final String finalFieldName = fieldName;
            FieldDefinition fieldDefinition = matchedFieldDefinition.orElseThrow(() -> new EntityDefinitionFieldNotFound(finalEntityDefinition, finalFieldName));

            if (fieldDefinition.isFieldRepresentingRelationship()) {
                EntityRelationshipDefinition relationship = entityRelationshipDefinitionRepository.getEntityRelationshipDefinitionByField(fieldDefinition.getId());
                entityDefinition = entityDefinitionRepository.getEntityDefinitionRelationshipGoesTo(relationship.getId());
                String relationshipAlias = "r" + traversalIndex;
                aliasAndName.alias = "e" + traversalIndex;
                builder.relationshipFromWithLabel(relationship.getName(), relationshipAlias);
                builder.nodeWithLabel(entityDefinition.getName(), aliasAndName.alias);
                aliasAndName.attribute = "name";
            } else {
                aliasAndName.attribute = fieldName;
            }

            fieldName = fieldPartQueue.poll();
        }

        return aliasAndName;
    }

    private void validateCriteria(EntitySearchRequestDto request) {
        if (request.getEntityType() != null) {
            EntityDefinition entityDefinition = getValidatedEntityDefinition(request.getEntityType());
            for (String fieldName : getAllCriteriaFields(request.getSearchCriteria())) {
                validateField(entityDefinition, fieldName);
            }
        }
    }

    private EntityDefinition getValidatedEntityDefinition(String entityType) {
        EntityDefinition entityDefinition = entityDefinitionRepository.getByName(entityType);
        if (entityDefinition == null) {
            throw new EntityDefinitionNotFound(entityType);
        }
        return entityDefinition;
    }

    private Set<String> getAllCriteriaFields(SearchCriteriaDto searchCriteria) {
        Set<String> fields = new LinkedHashSet<>();
        populateCriteriaFields(fields, searchCriteria);
        return fields;
    }

    private void populateCriteriaFields(Set<String> fields, SearchCriteriaDto searchCriteria) {
        if (searchCriteria.getField() != null) {
            fields.add(searchCriteria.getField());
        } else if (searchCriteria.getGroupedCriteria() != null) {
            for (SearchCriteriaDto subCriteria : searchCriteria.getGroupedCriteria()) {
                populateCriteriaFields(fields, subCriteria);
            }
        }
    }

    private void validateField(EntityDefinition entityDefinition, String fieldName) {
        String[] fieldParts = fieldName.split("\\.");
        Optional<FieldDefinition> matchedFieldDefinition = entityDefinition.getFieldByName(fieldParts[0]);
        FieldDefinition fieldDefinition = matchedFieldDefinition.orElseThrow(() -> new EntityDefinitionFieldNotFound(entityDefinition, fieldParts[0]));

        if (fieldDefinition.isFieldRepresentingRelationship() && fieldParts.length > 1) {
            EntityRelationshipDefinition relationship = entityRelationshipDefinitionRepository.getEntityRelationshipDefinitionByField(fieldDefinition.getId());
            EntityDefinition relationshipGoesToEntityDefinition = entityDefinitionRepository.getEntityDefinitionRelationshipGoesTo(relationship.getId());
            validateField(relationshipGoesToEntityDefinition, fieldName.substring(fieldName.indexOf('.') + 1));
        }
    }

    private NeoCypherQueryBuilder getBuilderForEntitySearch(EntitySearchRequestDto request) {
        NeoCypherQueryBuilder builder = new NeoCypherQueryBuilder(QueryType.MATCH);
        if (request.getEntityType() == null) {
            builder.nodeWithLabel("EntityDefinition", "ed").relationshipToWithLabel("METADATA", "md").anyNode(ENTITY_ALIAS);
        } else {
            builder.nodeWithLabel(request.getEntityType(), ENTITY_ALIAS);
        }
        return builder;
    }

    private EntitySearchResultsPageDto executeQuery(NeoCypherQuery cypherQuery, EntitySearchRequestDto request) {
        Result result = session.query(cypherQuery.getQuery(), cypherQuery.getParameters());
        EntitySearchResultsPageDto searchResultsPageDto = convertResultToResultPage(result);
        searchResultsPageDto.setPagingSummary(getPageSummary(request, cypherQuery, searchResultsPageDto.getPage()));
        return searchResultsPageDto;
    }

    private EntitySearchResultsPageDto convertResultToResultPage(Result result) {
        EntitySearchResultsPageDto resultsPage = new EntitySearchResultsPageDto();

        List<EntityDto> entityDtos = convertResultsToEntityDtos(result);
        resultsPage.setPage(entityDtos);
        return resultsPage;
    }

    private List<EntityDto> convertResultsToEntityDtos(Result result) {
        List<EntityDto> foundEntities = new ArrayList<>();
        result.forEach(item -> {
            Object entityNode = item.get(ENTITY_ALIAS);
            if (entityNode instanceof NodeModel) {
                foundEntities.add(convertResultToEntityDto((NodeModel) entityNode));
            }

        });
        return foundEntities;
    }

    private EntityDto convertResultToEntityDto(NodeModel entityNode) {
        EntityDto entity = new EntityDto();
        entity.setType(entityNode.getLabels()[0]);
        entity.setAttributes(getNodeAttributes(entityNode));
        entity.setUuid(String.valueOf(entity.getAttributes().get("uuid")));
        return entity;
    }

    private Map<String, Object> getNodeAttributes(NodeModel entityNode) {
        Map<String, Object> attributes = new LinkedHashMap<>();

        for (Property<String, Object> nodeProperty : entityNode.getPropertyList()) {
            attributes.put(nodeProperty.getKey(), nodeProperty.getValue());
        }

        return attributes;
    }

    private SearchResultPageSummaryDto getPageSummary(EntitySearchRequestDto request, NeoCypherQuery cypherQuery, List<EntityDto> entityDtos) {
        SearchResultPageSummaryDto pageSummary = new SearchResultPageSummaryDto();

        int startIndex = request.getStartIndex() == null ? 0 : request.getStartIndex();
        pageSummary.setStartIndex(startIndex);

        if (isQueryRequiredForTotalRowCount(request, entityDtos)) {
            pageSummary.setTotalResults(entityDtos.size());
            pageSummary.setPageSize(entityDtos.size());
        } else {
            pageSummary.setTotalResults(getTotalRowCountFromQuery(cypherQuery));
            pageSummary.setPageSize(request.getPageSize());
        }

        return pageSummary;
    }

    private boolean isQueryRequiredForTotalRowCount(EntitySearchRequestDto request, List<EntityDto> entityDtos) {
        return request.getPageSize() == null || entityDtos.size() < request.getPageSize();
    }

    private int getTotalRowCountFromQuery(NeoCypherQuery cypherQuery) {
        Result countResult = session.query(cypherQuery.getCountQuery(), cypherQuery.getParameters());
        Object singleResult = countResult.iterator().next().get("count(e)");
        return ((Long) singleResult).intValue();
    }

    private class AliasAndName {
        private String alias;
        private String attribute;
    }
}
