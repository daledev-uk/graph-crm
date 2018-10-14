package com.daledev.graphcrm.api.dao.neo4j;

import org.junit.Before;
import org.junit.Test;

import java.util.LinkedHashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

/**
 * @author dale.ellis
 * @since 03/10/2018
 */
public class JacksonCypherJsonSerializerTest {
    private JacksonCypherJsonSerializer cypherJsonSerializer;

    @Before
    public void setUp() throws Exception {
        cypherJsonSerializer = new JacksonCypherJsonSerializer();
        cypherJsonSerializer.setupMappers();
    }

    @Test
    public void toCypherJson() {
        // Given
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("textField", "Text Value");
        map.put("numberField", 180);
        map.put("booleanField", true);

        // When
        String json = cypherJsonSerializer.toCypherJson(map);

        // Then
        assertEquals("{textField:\"Text Value\",numberField:180,booleanField:true}", json);
    }
}