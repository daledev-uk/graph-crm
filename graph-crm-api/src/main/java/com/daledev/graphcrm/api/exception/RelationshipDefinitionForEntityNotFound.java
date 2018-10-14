package com.daledev.graphcrm.api.exception;

/**
 * @author dale.ellis
 * @since 12/10/2018
 */
public class RelationshipDefinitionForEntityNotFound extends RuntimeException {
    /**
     * @param relationshipLabel
     * @param entityUuid
     */
    public RelationshipDefinitionForEntityNotFound(String relationshipLabel, String entityUuid) {
        super("Relationship definition with label '" + relationshipLabel + "' not found for entity with UUID : " + entityUuid);
    }
}
