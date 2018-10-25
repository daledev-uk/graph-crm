package com.daledev.graphcrm.api.dao.neo4j.query;

import java.util.Map;

/**
 * @author dale.ellis
 * @since 18/10/2018
 */
public class NeoCypherQuery {
    private String query;
    private String countQuery;
    private Map<String, Object> parameters;

    NeoCypherQuery(String query, String countQuery, Map<String, Object> parameters) {
        this.query = query;
        this.countQuery = countQuery;
        this.parameters = parameters;
    }

    public String getQuery() {
        return query;
    }

    public String getCountQuery() {
        return countQuery;
    }

    public Map<String, Object> getParameters() {
        return parameters;
    }
}
