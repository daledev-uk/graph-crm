package com.daledev.graphcrm.api.repository;

import com.daledev.graphcrm.api.domain.EntityDefinition;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

/**
 * @author dale.ellis
 * @since 07/10/2018
 */
@Repository
public interface EntityDefinitionRepository extends Neo4jRepository<EntityDefinition, Long> {
    /**
     * @param name
     * @return
     */
    EntityDefinition getByName(String name);

    /**
     * Returns the definition for the entity matching the provided UUID
     *
     * @param uuid
     * @return
     */
    @Query("MATCH (entity {uuid: {0}})-[:METADATA]->(ed:EntityDefinition) RETURN ed")
    EntityDefinition getEntityDefinitionByEntityUuid(String uuid);

}
