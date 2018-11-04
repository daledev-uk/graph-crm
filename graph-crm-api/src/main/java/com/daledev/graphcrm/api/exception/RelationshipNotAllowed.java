package com.daledev.graphcrm.api.exception;

import com.daledev.graphcrm.api.domain.EntityDefinition;

/**
 * @author dale.ellis
 * @since 12/10/2018
 */
public class RelationshipNotAllowed extends RuntimeException {
    /**
     * @param entityDefinition
     */
    public RelationshipNotAllowed(EntityDefinition entityDefinition) {
        super("Entity definition '" + entityDefinition.getName() + "' is not defined to be used on the given relationship");
    }
}
