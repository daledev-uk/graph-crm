package com.daledev.graphcrm.api.dto.detail;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author dale.ellis
 * @since 03/10/2018
 */
public class EntityDto {
    private String type;
    private String uuid;
    private Map<String, Object> attributes = new LinkedHashMap<>();

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public Map<String, Object> getAttributes() {
        return attributes;
    }

    public void setAttributes(Map<String, Object> attributes) {
        this.attributes = attributes;
    }
}
