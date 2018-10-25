package com.daledev.graphcrm.api.service;

import com.daledev.graphcrm.api.dto.response.EntitySearchResultsPageDto;
import com.daledev.graphcrm.api.dto.search.EntitySearchRequestDto;

/**
 * @author dale.ellis
 * @since 16/10/2018
 */
public interface EntitySearchService {

    /**
     * @param request
     * @return
     */
    EntitySearchResultsPageDto search(EntitySearchRequestDto request);
}
