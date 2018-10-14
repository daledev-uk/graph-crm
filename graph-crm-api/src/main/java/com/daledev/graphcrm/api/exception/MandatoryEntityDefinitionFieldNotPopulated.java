package com.daledev.graphcrm.api.exception;

import com.daledev.graphcrm.api.domain.EntityDefinition;

/**
 * @author dale.ellis
 * @since 08/10/2018
 */
public class MandatoryEntityDefinitionFieldNotPopulated extends RuntimeException {
    /**
     * @param entityDefinition
     * @param fieldName
     */
    public MandatoryEntityDefinitionFieldNotPopulated(EntityDefinition entityDefinition, String fieldName) {
        super("Entity definition '" + entityDefinition.getName() + "' field : '" + fieldName + "' must be populated");
    }
}
