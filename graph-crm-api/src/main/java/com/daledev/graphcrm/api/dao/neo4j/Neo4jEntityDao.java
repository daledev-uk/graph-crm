package com.daledev.graphcrm.api.dao.neo4j;

import com.daledev.graphcrm.api.dao.EntityDao;
import com.daledev.graphcrm.api.domain.Entity;
import org.neo4j.ogm.model.Property;
import org.neo4j.ogm.model.Result;
import org.neo4j.ogm.response.model.NodeModel;
import org.neo4j.ogm.session.Session;
import org.springframework.stereotype.Repository;

import java.util.*;

/**
 * @author dale.ellis
 * @since 02/10/2018
 */
@Repository
public class Neo4jEntityDao implements EntityDao {
    private Session session;
    private CypherJsonSerializer cypherJsonSerializer;

    /**
     * @param session
     * @param cypherJsonSerializer
     */
    public Neo4jEntityDao(Session session, CypherJsonSerializer cypherJsonSerializer) {
        this.session = session;
        this.cypherJsonSerializer = cypherJsonSerializer;
    }

    @Override
    public Optional<Entity> findById(String entityId) {
        String cypher = String.format("MATCH (entity {uuid: '%s'}) RETURN labels(entity), entity", entityId);
        Result createResult = session.query(cypher, Collections.emptyMap(), true);

        Iterator<Map<String, Object>> foundNodes = createResult.queryResults().iterator();
        if (foundNodes.hasNext()) {
            Map<String, Object> returnedRow = foundNodes.next();
            Entity entity = new Entity(getLabel(returnedRow.get("labels(entity)")), entityId);
            Map<String, Object> nodeAttributes = getNodeAttributes(returnedRow.get("entity"));
            nodeAttributes.forEach(entity::addAttributeValue);
            return Optional.ofNullable(entity);
        } else {
            return Optional.empty();
        }
    }

    private String getLabel(Object labelData) {
        List<String> labels = getLabels(labelData);
        return labels.size() == 1 ? labels.get(0) : null;
    }

    private List<String> getLabels(Object labelData) {
        if (labelData instanceof String[]) {
            return Arrays.asList((String[]) labelData);
        }
        return new ArrayList<>();
    }

    private Map<String, Object> getNodeAttributes(Object labelData) {
        Map<String, Object> attributes = new LinkedHashMap<>();
        if (labelData instanceof NodeModel) {
            NodeModel nodeModel = (NodeModel) labelData;
            for (Property<String, Object> nodeProperty : nodeModel.getPropertyList()) {
                attributes.put(nodeProperty.getKey(), nodeProperty.getValue());
            }
        }
        return attributes;
    }

    @Override
    public Entity createEntity(String entityType, Map<String, Object> values) {
        Map<String, Object> nodeProperties = new HashMap<>(values);
        String uuid = UUID.randomUUID().toString();
        nodeProperties.put("uuid", uuid);

        String json = cypherJsonSerializer.toCypherJson(nodeProperties);
        String cypherEntitySyntax = "(n:" + entityType + " " + json + ")";
        String cypher = "CREATE " + cypherEntitySyntax;

        Result createResult = session.query(cypher, Collections.emptyMap());

        String defintiionRelationshipCypher = String.format("MATCH (fromNode {uuid: '%s'}), (toNode:EntityDefinition {name: '%s'}) CREATE (fromNode)-[:METADATA]->(toNode)", uuid, entityType);
        createResult = session.query(defintiionRelationshipCypher, Collections.emptyMap());

        Entity entity = new Entity(entityType, uuid);
        nodeProperties.forEach(entity::addAttributeValue);

        return entity;
    }
}
