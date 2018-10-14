package com.daledev.graphcrm.api.dao.neo4j;

/**
 * @author dale.ellis
 * @since 02/10/2018
 */
public interface CypherJsonSerializer {

    /**
     * Create json without quotes around its attributes
     *
     * @param input
     * @return
     */
    String toCypherJson(Object input);

}
