package com.daledev.graphcrm.api.exception;

/**
 * @author dale.ellis
 * @since 11/10/2018
 */
public class EntityDefinitionAlreadyExists extends RuntimeException {
    /**
     * @param entityType
     */
    public EntityDefinitionAlreadyExists(String entityType) {
        super("Entity definition with name alreay exists : " + entityType);
    }
}
