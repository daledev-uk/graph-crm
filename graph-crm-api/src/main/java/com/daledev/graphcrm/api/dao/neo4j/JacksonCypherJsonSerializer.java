package com.daledev.graphcrm.api.dao.neo4j;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

/**
 * @author dale.ellis
 * @since 02/10/2018
 */
@Service
public class JacksonCypherJsonSerializer implements CypherJsonSerializer {
    private ObjectMapper cypherJsonMapper;

    @Override
    public String toCypherJson(Object input) {
        try {
            // Remove the quotes from attribute names
            return cypherJsonMapper.writeValueAsString(input);
        } catch (JsonProcessingException e) {
            return "{}";
        }
    }

    @PostConstruct
    void setupMappers() {
        cypherJsonMapper = new ObjectMapper();
        cypherJsonMapper.configure(JsonGenerator.Feature.QUOTE_FIELD_NAMES, false);
    }
}
