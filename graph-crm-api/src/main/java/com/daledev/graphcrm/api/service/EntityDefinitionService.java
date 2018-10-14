package com.daledev.graphcrm.api.service;

import com.daledev.graphcrm.api.dto.request.CreateEntityDefinitionRequestDto;
import com.daledev.graphcrm.api.dto.request.CreateEntityRequestDto;

/**
 * @author dale.ellis
 * @since 07/10/2018
 */
public interface EntityDefinitionService {
    /**
     * Validates the label match's a defined allowed entity. Validates fields supplied are defined and correct data type
     * and that mandatory fields supplied
     *
     * @param request
     */
    void validateEntity(CreateEntityRequestDto request);

    /**
     *
     * @param createRequest
     */
    void createEntityDefinition(CreateEntityDefinitionRequestDto createRequest);
}
