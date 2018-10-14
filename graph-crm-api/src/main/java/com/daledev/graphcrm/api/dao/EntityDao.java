package com.daledev.graphcrm.api.dao;

import com.daledev.graphcrm.api.domain.Entity;

import java.util.Map;
import java.util.Optional;

/**
 * @author dale.ellis
 * @since 02/10/2018
 */
public interface EntityDao {

    /**
     * @param entityId
     * @return
     */
    Optional<Entity> findById(String entityId);

    /**
     * @param entityType
     * @param values
     * @return
     */
    Entity createEntity(String entityType, Map<String, Object> values);


}
