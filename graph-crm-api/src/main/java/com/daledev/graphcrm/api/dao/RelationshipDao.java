package com.daledev.graphcrm.api.dao;

import java.util.Map;

/**
 * @author dale.ellis
 * @since 02/10/2018
 */
public interface RelationshipDao {

    /**
     * @param relationshipType
     * @param fromNode
     * @param toNode
     * @param relationshipValues
     */
    void createRelationship(String relationshipType, String fromNode, String toNode, Map<String, Object> relationshipValues);

}
