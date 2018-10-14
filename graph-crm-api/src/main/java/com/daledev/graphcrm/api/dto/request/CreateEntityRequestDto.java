package com.daledev.graphcrm.api.dto.request;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author dale.ellis
 * @since 01/10/2018
 */
public class CreateEntityRequestDto implements Serializable {
    private String entityType;
    private Map<String, Object> values = new LinkedHashMap<>();

    public String getEntityType() {
        return entityType;
    }

    public void setEntityType(String entityType) {
        this.entityType = entityType;
    }

    public Map<String, Object> getValues() {
        return values;
    }

    public void setValues(Map<String, Object> values) {
        this.values = values;
    }
}
