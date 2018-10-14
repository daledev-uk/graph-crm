package com.daledev.graphcrm.api.service;

import com.daledev.graphcrm.api.dao.EntityDao;
import com.daledev.graphcrm.api.domain.Entity;
import com.daledev.graphcrm.api.dto.detail.EntityDto;
import com.daledev.graphcrm.api.dto.request.CreateEntityRequestDto;
import com.daledev.graphcrm.api.exception.EntityNotFound;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * @author dale.ellis
 * @since 01/10/2018
 */
@Transactional
@Service
public class EntityServiceImpl implements EntityService {
    private static final Logger log = LoggerFactory.getLogger(EntityServiceImpl.class);

    private EntityDao entityDao;
    private EntityDefinitionService entityDefinitionService;
    private MappingService mappingService;

    /**
     * @param entityDao
     * @param entityDefinitionService
     * @param mappingService
     */
    public EntityServiceImpl(EntityDao entityDao, EntityDefinitionService entityDefinitionService, MappingService mappingService) {
        this.entityDao = entityDao;
        this.entityDefinitionService = entityDefinitionService;
        this.mappingService = mappingService;
    }

    @Override
    public EntityDto getEntity(String entityId) {
        log.debug("Getting entity for ID '{}'.", entityId);
        Entity entity = getEntityById(entityId);
        return mappingService.map(entity, EntityDto.class);
    }

    @Override
    public EntityDto createEntity(CreateEntityRequestDto request) {
        log.debug("Creating entity of type '{}'. Values : {}", request.getEntityType(), request.getValues());
        entityDefinitionService.validateEntity(request);
        Entity entity = entityDao.createEntity(request.getEntityType(), request.getValues());
        return mappingService.map(entity, EntityDto.class);
    }

    private Entity getEntityById(String entityId) {
        Optional<Entity> entityById = entityDao.findById(entityId);
        return entityById.orElseThrow(() -> new EntityNotFound(entityId));
    }
}
