package com.daledev.graphcrm.api.dao.neo4j.query;

/**
 * @author dale.ellis
 * @since 18/10/2018
 */
public class Parameter {
    private String parameterName;
    private Object value;

    public Parameter(String parameterName, Object value) {
        this.parameterName = parameterName;
        this.value = value;
    }

    public String getParameterName() {
        return parameterName;
    }

    public Object getValue() {
        return value;
    }
}
