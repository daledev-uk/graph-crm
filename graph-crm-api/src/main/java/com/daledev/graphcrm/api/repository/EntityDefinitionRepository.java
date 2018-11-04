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

    /**
     *
     * @param relationshipId
     * @return
     */
    //@Query("MATCH (r:EntityRelationshipDefinition)-[:GOES_TO]->(ed:EntityDefinition) WHERE ID(r) = {0} RETURN ed")
    @Query("MATCH (r:EntityRelationshipDefinition)-[:GOES_TO]->(ed:EntityDefinition) WHERE ID(r) = {0} WITH ed RETURN ed,[ [ (ed)-[r_f1:`FIELD`]->(f1:`FieldDefinition`) | [ r_f1, f1 ] ] ], ID(ed)")
    EntityDefinition getEntityDefinitionRelationshipGoesTo(Long relationshipId);

}
