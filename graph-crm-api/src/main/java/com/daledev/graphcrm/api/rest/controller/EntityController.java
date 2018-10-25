package com.daledev.graphcrm.api.rest.controller;

import com.daledev.graphcrm.api.dto.detail.EntityDto;
import com.daledev.graphcrm.api.dto.detail.PersonDto;
import com.daledev.graphcrm.api.dto.request.CreateEntityRequestDto;
import com.daledev.graphcrm.api.rest.constants.RestPaths;
import com.daledev.graphcrm.api.service.EntityService;
import org.springframework.web.bind.annotation.*;

/**
 * @author dale.ellis
 * @since 01/10/2018
 */
@RestController
@RequestMapping(RestPaths.ENTITY)
public class EntityController {

    private EntityService entityService;

    /**
     *
     * @param entityService
     */
    public EntityController(EntityService entityService) {
        this.entityService = entityService;
    }

    /**
     * @param entityId
     * @return
     */
    @GetMapping("/{entityId}")
    public EntityDto getEntity(@PathVariable String entityId) {
        return entityService.getEntity(entityId);
    }

    /**
     * @param createRequest
     * @return
     */
    @PostMapping("/create")
    public EntityDto createEntity(@RequestBody CreateEntityRequestDto createRequest) {
        return entityService.createEntity(createRequest);
    }

}
