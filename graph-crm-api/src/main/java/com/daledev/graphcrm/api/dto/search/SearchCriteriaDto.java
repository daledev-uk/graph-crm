package com.daledev.graphcrm.api.dto.search;

import com.daledev.graphcrm.api.constants.SearchLogicalOperator;
import com.daledev.graphcrm.api.constants.SearchOperator;

import java.util.ArrayList;
import java.util.List;

/**
 * @author dale.ellis
 * @since 16/10/2018
 */
public class SearchCriteriaDto {
    private String field;
    private SearchOperator operator;
    private SearchLogicalOperator logicalOperator;
    private Object value;
    private List<SearchCriteriaDto> groupedCriteria = new ArrayList<>();

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public SearchOperator getOperator() {
        return operator;
    }

    public void setOperator(SearchOperator operator) {
        this.operator = operator;
    }

    public SearchLogicalOperator getLogicalOperator() {
        return logicalOperator;
    }

    public void setLogicalOperator(SearchLogicalOperator logicalOperator) {
        this.logicalOperator = logicalOperator;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public List<SearchCriteriaDto> getGroupedCriteria() {
        return groupedCriteria;
    }

    public void setGroupedCriteria(List<SearchCriteriaDto> groupedCriteria) {
        this.groupedCriteria = groupedCriteria;
    }
}
