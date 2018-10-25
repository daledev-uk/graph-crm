package com.daledev.graphcrm.api.service;

import com.daledev.graphcrm.api.dao.EntitySearchDao;
import com.daledev.graphcrm.api.dto.response.EntitySearchResultsPageDto;
import com.daledev.graphcrm.api.dto.search.EntitySearchRequestDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author dale.ellis
 * @since 16/10/2018
 */
@Transactional(readOnly = true)
@Service
public class EntitySearchServiceImpl implements EntitySearchService {
    private EntitySearchDao entitySearchDao;

    public EntitySearchServiceImpl(EntitySearchDao entitySearchDao) {
        this.entitySearchDao = entitySearchDao;
    }

    @Override
    public EntitySearchResultsPageDto search(EntitySearchRequestDto request) {
        return entitySearchDao.search(request);
    }
}
