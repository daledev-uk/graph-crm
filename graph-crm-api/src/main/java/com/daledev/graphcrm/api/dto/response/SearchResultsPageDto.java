package com.daledev.graphcrm.api.dto.response;

import com.daledev.graphcrm.api.dto.search.SearchResultPageSummaryDto;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.ArrayList;
import java.util.List;

/**
 * @author dale.ellis
 * @since 16/10/2018
 */
public abstract class SearchResultsPageDto<T> {
    private List<T> page = new ArrayList<>();
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private SearchResultPageSummaryDto pagingSummary;

    public List<T> getPage() {
        return page;
    }

    public void setPage(List<T> page) {
        this.page = page;
    }

    public SearchResultPageSummaryDto getPagingSummary() {
        return pagingSummary;
    }

    public void setPagingSummary(SearchResultPageSummaryDto pagingSummary) {
        this.pagingSummary = pagingSummary;
    }
}
