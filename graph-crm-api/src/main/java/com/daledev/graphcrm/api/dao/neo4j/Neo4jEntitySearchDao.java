package com.daledev.graphcrm.api.dao.neo4j;

import com.daledev.graphcrm.api.dao.EntitySearchDao;
import com.daledev.graphcrm.api.dao.neo4j.query.NeoCypherQuery;
import com.daledev.graphcrm.api.dao.neo4j.query.NeoCypherQueryBuilder;
import com.daledev.graphcrm.api.dao.neo4j.query.QueryType;
import com.daledev.graphcrm.api.dto.detail.EntityDto;
import com.daledev.graphcrm.api.dto.response.EntitySearchResultsPageDto;
import com.daledev.graphcrm.api.dto.search.EntitySearchRequestDto;
import com.daledev.graphcrm.api.dto.search.SearchCriteriaDto;
import com.daledev.graphcrm.api.dto.search.SearchResultPageSummaryDto;
import org.neo4j.ogm.model.Property;
import org.neo4j.ogm.model.Result;
import org.neo4j.ogm.response.model.NodeModel;
import org.neo4j.ogm.session.Session;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author dale.ellis
 * @since 16/10/2018
 */
@Repository
public class Neo4jEntitySearchDao implements EntitySearchDao {
    public static final String ENTITY_ALIAS = "e";
    private Session session;

    public Neo4jEntitySearchDao(Session session) {
        this.session = session;
    }

    @Override
    public EntitySearchResultsPageDto search(EntitySearchRequestDto request) {
        NeoCypherQueryBuilder builder = getBuilderForEntitySearch(request);

        createWhereCondition(request.getSearchCriteria(), builder);

        builder.returnNodes(ENTITY_ALIAS);
        builder.skipFirstNumberOfResults(request.getStartIndex());
        builder.resultLimit(request.getPageSize());
        NeoCypherQuery cypherQuery = builder.build();

        return executeQuery(cypherQuery, request);
    }

    private void createWhereCondition(SearchCriteriaDto searchCriteria, NeoCypherQueryBuilder builder) {
        if (searchCriteria != null) {
            List<SearchCriteriaDto> groupedCriteria = searchCriteria.getGroupedCriteria();
            if (groupedCriteria != null && !groupedCriteria.isEmpty()) {
                builder.subCondition(searchCriteria.getLogicalOperator());
                for (SearchCriteriaDto subCriteria : groupedCriteria) {
                    createWhereCondition(subCriteria, builder);
                }
            } else {
                builder.addWhereCondition(ENTITY_ALIAS, searchCriteria.getField(), searchCriteria.getOperator(), searchCriteria.getValue());
            }
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
}
