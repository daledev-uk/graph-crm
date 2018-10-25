package com.daledev.graphcrm.api.dto.search;

/**
 * @author dale.ellis
 * @since 16/10/2018
 */
public class SearchResultPageSummaryDto {
    private int startIndex;
    private int pageSize;
    private int totalResults;

    public int getStartIndex() {
        return startIndex;
    }

    public void setStartIndex(int startIndex) {
        this.startIndex = startIndex;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(int totalResults) {
        this.totalResults = totalResults;
    }
}
