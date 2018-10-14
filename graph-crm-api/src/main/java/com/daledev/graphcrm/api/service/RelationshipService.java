package com.daledev.graphcrm.api.service;

import com.daledev.graphcrm.api.dto.request.CreateRelationshipRequestDto;

/**
 * @author dale.ellis
 * @since 01/10/2018
 */
public interface RelationshipService {

    /**
     * @param request
     */
    void createRelationship(CreateRelationshipRequestDto request);
}
