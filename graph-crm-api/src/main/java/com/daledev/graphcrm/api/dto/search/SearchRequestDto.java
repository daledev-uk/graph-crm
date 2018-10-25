package com.daledev.graphcrm.api.dto.search;

/**
 * @author dale.ellis
 * @since 16/10/2018
 */
public class SearchRequestDto {
    private SearchCriteriaDto searchCriteria;
    private SortDto sortDto;
    private Integer startIndex;
    private Integer pageSize;

    public SearchCriteriaDto getSearchCriteria() {
        return searchCriteria;
    }

    public void setSearchCriteria(SearchCriteriaDto searchCriteria) {
        this.searchCriteria = searchCriteria;
    }

    public SortDto getSortDto() {
        return sortDto;
    }

    public void setSortDto(SortDto sortDto) {
        this.sortDto = sortDto;
    }

    public Integer getStartIndex() {
        return startIndex;
    }

    public void setStartIndex(Integer startIndex) {
        this.startIndex = startIndex;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }
}
