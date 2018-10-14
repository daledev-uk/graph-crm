package com.daledev.graphcrm.api.service;

import com.daledev.graphcrm.api.dto.detail.EntityDto;
import com.daledev.graphcrm.api.dto.request.CreateEntityRequestDto;

/**
 * @author dale.ellis
 * @since 01/10/2018
 */
public interface EntityService {

    /**
     * @param entityId
     * @return
     */
    EntityDto getEntity(String entityId);

    /**
     * @param request
     * @return
     */
    EntityDto createEntity(CreateEntityRequestDto request);


}
