package com.daledev.graphcrm.api.rest.controller;

import com.daledev.graphcrm.api.dto.request.CreateRelationshipRequestDto;
import com.daledev.graphcrm.api.service.RelationshipService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author dale.ellis
 * @since 01/10/2018
 */
@RestController
@RequestMapping("/api/relationship")
public class RelationshipController {

    private RelationshipService relationshipService;

    public RelationshipController(RelationshipService relationshipService) {
        this.relationshipService = relationshipService;
    }

    /**
     * @param createRequest
     */
    @PostMapping("/create")
    public void createRelationship(@RequestBody CreateRelationshipRequestDto createRequest) {
        relationshipService.createRelationship(createRequest);
    }

}
