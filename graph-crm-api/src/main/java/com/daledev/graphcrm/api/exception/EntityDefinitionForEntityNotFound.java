package com.daledev.graphcrm.api.exception;

/**
 * @author dale.ellis
 * @since 12/10/2018
 */
public class EntityDefinitionForEntityNotFound extends RuntimeException {
    /**
     * @param entityUuid
     */
    public EntityDefinitionForEntityNotFound(String entityUuid) {
        super("Entity definition not found for entity with UUID : " + entityUuid);
    }
}
