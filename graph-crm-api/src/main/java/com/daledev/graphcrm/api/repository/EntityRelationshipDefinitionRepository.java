package com.daledev.graphcrm.api.repository;

import com.daledev.graphcrm.api.domain.EntityRelationshipDefinition;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author dale.ellis
 * @since 12/10/2018
 */
@Repository
public interface EntityRelationshipDefinitionRepository extends Neo4jRepository<EntityRelationshipDefinition, Long> {
    /**
     * @param name
     * @return
     */
    Optional<EntityRelationshipDefinition> getByName(String name);
}
