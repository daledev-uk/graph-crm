package com.daledev.graphcrm.api.exception;

import com.daledev.graphcrm.api.domain.EntityDefinition;

/**
 * @author dale.ellis
 * @since 08/10/2018
 */
public class EntityDefinitionFieldNotFound extends RuntimeException {
    /**
     * @param entityDefinition
     * @param fieldName
     */
    public EntityDefinitionFieldNotFound(EntityDefinition entityDefinition, String fieldName) {
        super("Entity definition '" + entityDefinition.getName() + "' has no field called : " + fieldName);
    }
}
