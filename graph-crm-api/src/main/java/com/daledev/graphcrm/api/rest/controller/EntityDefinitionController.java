package com.daledev.graphcrm.api.rest.controller;

import com.daledev.graphcrm.api.dto.request.CreateEntityDefinitionRequestDto;
import com.daledev.graphcrm.api.dto.request.UpdateEntityDefinitionRequestDto;
import com.daledev.graphcrm.api.rest.constants.RestPaths;
import com.daledev.graphcrm.api.service.EntityDefinitionService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author dale.ellis
 * @since 11/10/2018
 */
@RestController
@RequestMapping(RestPaths.ENTITY_DEFINITION)
public class EntityDefinitionController {
    private EntityDefinitionService entityDefinitionService;

    public EntityDefinitionController(EntityDefinitionService entityDefinitionService) {
        this.entityDefinitionService = entityDefinitionService;
    }

    /**
     * @param createRequest
     */
    @PostMapping("/create")
    public void createEntityDefinition(@RequestBody CreateEntityDefinitionRequestDto createRequest) {
        entityDefinitionService.createEntityDefinition(createRequest);
    }

    /**
     * @param updateRequest
     */
    @PostMapping("/update")
    public void updateEntityDefinition(@RequestBody UpdateEntityDefinitionRequestDto updateRequest) {
        entityDefinitionService.updateEntityDefinition(updateRequest);
    }


}
