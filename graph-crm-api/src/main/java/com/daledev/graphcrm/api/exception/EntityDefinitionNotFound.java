package com.daledev.graphcrm.api.exception;

/**
 * @author dale.ellis
 * @since 08/10/2018
 */
public class EntityDefinitionNotFound extends RuntimeException {
    /**
     * @param entityType
     */
    public EntityDefinitionNotFound(String entityType) {
        super("Entity definition not found with type : " + entityType);
    }
}
