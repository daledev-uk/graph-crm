package com.daledev.graphcrm.api.service;

import com.daledev.graphcrm.api.dao.RelationshipDao;
import com.daledev.graphcrm.api.domain.EntityDefinition;
import com.daledev.graphcrm.api.domain.EntityRelationshipDefinition;
import com.daledev.graphcrm.api.dto.request.CreateRelationshipRequestDto;
import com.daledev.graphcrm.api.exception.EntityDefinitionForEntityNotFound;
import com.daledev.graphcrm.api.exception.RelationshipDefinitionForEntityNotFound;
import com.daledev.graphcrm.api.exception.RelationshipNotAllowed;
import com.daledev.graphcrm.api.repository.EntityDefinitionRepository;
import com.daledev.graphcrm.api.repository.EntityRelationshipDefinitionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * @author dale.ellis
 * @since 02/10/2018
 */
@Transactional
@Service
public class RelationshipServiceImpl implements RelationshipService {
    private EntityDefinitionRepository entityDefinitionRepository;
    private RelationshipDao relationshipDao;
    private EntityRelationshipDefinitionRepository entityRelationshipDefinitionRepository;

    /**
     * @param entityDefinitionRepository
     * @param relationshipDao
     * @param entityRelationshipDefinitionRepository
     */
    public RelationshipServiceImpl(EntityDefinitionRepository entityDefinitionRepository, RelationshipDao relationshipDao, EntityRelationshipDefinitionRepository entityRelationshipDefinitionRepository) {
        this.entityDefinitionRepository = entityDefinitionRepository;
        this.relationshipDao = relationshipDao;
        this.entityRelationshipDefinitionRepository = entityRelationshipDefinitionRepository;
    }

    @Override
    public void createRelationship(CreateRelationshipRequestDto request) {
        EntityDefinition fromEntityDefinition = getEntityDefinitionByEntityUuid(request.getFromEntityId());
        EntityDefinition toEntityDefinition = getEntityDefinitionByEntityUuid(request.getToEntityId());

        EntityRelationshipDefinition entityRelationshipDefinition = getValidRelationshipDefinition(request);
        if (!entityRelationshipDefinition.getRelationshipFrom().equals(fromEntityDefinition)) {
            throw new RelationshipNotAllowed(fromEntityDefinition);
        }
        if (entityRelationshipDefinition.isEntityAllowed(toEntityDefinition)) {
            relationshipDao.createRelationship(request.getRelationshipType(), request.getFromEntityId(), request.getToEntityId(), request.getRelationshipAttributes());
        } else {
            throw new RelationshipNotAllowed(toEntityDefinition);
        }
    }

    private EntityDefinition getEntityDefinitionByEntityUuid(String entityId) {
        EntityDefinition entityDefinitionByEntityUuid = entityDefinitionRepository.getEntityDefinitionByEntityUuid(entityId);
        if (entityDefinitionByEntityUuid == null) {
            throw new EntityDefinitionForEntityNotFound(entityId);
        }
        return entityDefinitionByEntityUuid;
    }

    private EntityRelationshipDefinition getValidRelationshipDefinition(CreateRelationshipRequestDto request) {
        Optional<EntityRelationshipDefinition> relationshipByName = entityRelationshipDefinitionRepository.getByName(request.getRelationshipType());
        return relationshipByName.orElseThrow(() -> new RelationshipDefinitionForEntityNotFound(request.getRelationshipType(), request.getFromEntityId()));
    }
}
