package com.daledev.graphcrm.api.dao.neo4j;

import com.daledev.graphcrm.api.dao.RelationshipDao;
import org.neo4j.ogm.session.Session;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.Map;

/**
 * @author dale.ellis
 * @since 02/10/2018
 */
@Repository
public class Neo4jRelationshipDao implements RelationshipDao {
    private static final String CREATE_RELATIONSHIP_CYPHER = "MATCH (fromNode {uuid: '%s'}), (toNode {uuid: '%s'}) CREATE (fromNode)-[:%s]->(toNode)";
    private Session session;

    /**
     * @param session
     */
    public Neo4jRelationshipDao(Session session) {
        this.session = session;
    }

    @Override
    public void createRelationship(String relationshipType, String fromNodeId, String toNodeId, Map<String, Object> relationshipValues) {
        String cypher = String.format(CREATE_RELATIONSHIP_CYPHER, fromNodeId, toNodeId, relationshipType);
        session.query(cypher, Collections.emptyMap());
    }
}
