package com.daledev.graphcrm.api.dao;

import com.daledev.graphcrm.api.dto.response.EntitySearchResultsPageDto;
import com.daledev.graphcrm.api.dto.search.EntitySearchRequestDto;

/**
 * @author dale.ellis
 * @since 16/10/2018
 */
public interface EntitySearchDao {
    /**
     * @param request
     * @return
     */
    EntitySearchResultsPageDto search(EntitySearchRequestDto request);
}
