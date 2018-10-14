package com.daledev.graphcrm.api.exception;

/**
 * @author dale.ellis
 * @since 04/10/2018
 */
public class EntityNotFound extends RuntimeException {
    /**
     * @param entityId
     */
    public EntityNotFound(String entityId) {
        super("Entity not found with UUID : " + entityId);
    }
}
