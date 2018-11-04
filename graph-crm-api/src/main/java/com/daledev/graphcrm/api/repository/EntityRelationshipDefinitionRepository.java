package com.daledev.graphcrm.api.repository;

import com.daledev.graphcrm.api.domain.EntityRelationshipDefinition;
import com.daledev.graphcrm.api.domain.FieldDefinition;
import org.springframework.data.neo4j.annotation.Query;
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

    /**
     *
     * @param fieldDefinitionId
     * @return
     */
    @Query("MATCH (rd:EntityRelationshipDefinition)<-[:RELATIONSHIP]-(f:FieldDefinition) WHERE id(f) = {0} RETURN rd")
    EntityRelationshipDefinition getEntityRelationshipDefinitionByField(Long fieldDefinitionId);
}
