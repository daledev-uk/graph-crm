package com.daledev.graphcrm.api.rest.controller;

import com.daledev.graphcrm.api.constants.SearchOperator;
import com.daledev.graphcrm.api.dto.response.EntitySearchResultsPageDto;
import com.daledev.graphcrm.api.dto.search.EntitySearchRequestDto;
import com.daledev.graphcrm.api.dto.search.SearchCriteriaDto;
import com.daledev.graphcrm.api.dto.search.SortDto;
import com.daledev.graphcrm.api.rest.constants.RestPaths;
import com.daledev.graphcrm.api.service.EntitySearchService;
import org.springframework.web.bind.annotation.*;

/**
 * @author dale.ellis
 * @since 16/10/2018
 */
@RestController
@RequestMapping(RestPaths.ENTITY_SEARCH)
public class EntitySearchController {
    private EntitySearchService entitySearchService;

    public EntitySearchController(EntitySearchService entitySearchService) {
        this.entitySearchService = entitySearchService;
    }

    /**
     * @param searchPhrase
     * @return
     */
    @GetMapping("/{searchPhrase}")
    public EntitySearchResultsPageDto searchByName(
            @PathVariable("searchPhrase") String searchPhrase,
            @RequestParam(value = "si", required = false) Integer startIndex,
            @RequestParam(value = "ps", required = false) Integer pageSize,
            @RequestParam(value = "s", required = false) SortDto sort
    ) {
        EntitySearchRequestDto searchRequest = new EntitySearchRequestDto();
        searchRequest.setSearchCriteria(new SearchCriteriaDto());
        searchRequest.getSearchCriteria().setField("name");
        searchRequest.getSearchCriteria().setOperator(SearchOperator.CONTAINS);
        searchRequest.getSearchCriteria().setValue(searchPhrase);
        searchRequest.setStartIndex(startIndex);
        searchRequest.setPageSize(pageSize);
        searchRequest.setSortDto(sort);

        return entitySearchService.search(searchRequest);
    }

    /**
     * @param criteria JSON representation of {@link SearchCriteriaDto}. Optionally base64 encoded
     * @return
     */
    @GetMapping("/")
    public EntitySearchResultsPageDto simpleSearch(
            @RequestParam("c") SearchCriteriaDto criteria,
            @RequestParam("t") String type,
            @RequestParam(value = "si", required = false) Integer startIndex,
            @RequestParam(value = "ps", required = false) Integer pageSize,
            @RequestParam(value = "s", required = false) SortDto sort
    ) {
        EntitySearchRequestDto searchRequest = new EntitySearchRequestDto();
        searchRequest.setEntityType(type);
        searchRequest.setSearchCriteria(criteria);
        searchRequest.setStartIndex(startIndex);
        searchRequest.setPageSize(pageSize);
        searchRequest.setSortDto(sort);
        return entitySearchService.search(searchRequest);
    }

}
