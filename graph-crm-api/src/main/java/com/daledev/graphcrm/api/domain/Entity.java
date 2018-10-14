package com.daledev.graphcrm.api.domain;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

/**
 * @author dale.ellis
 * @since 03/10/2018
 */
public class Entity {
    private static final String UUID_ATTRIBUTE = "uuid";

    private String type;
    private Map<String, Object> attributes = new LinkedHashMap<>();

    /**
     * @param type
     * @param uuid
     */
    public Entity(String type, String uuid) {
        this.type = type;
        addAttributeValue(UUID_ATTRIBUTE, uuid);
    }

    public String getType() {
        return type;
    }

    /**
     * @return
     */
    public String getUuid() {
        return getAttributeValueAsString(UUID_ATTRIBUTE);
    }

    /**
     * Returns copy of the attributes for this entity
     *
     * @return
     */
    public Map<String, Object> getAttributes() {
        return new LinkedHashMap<>(attributes);
    }

    /**
     * @param attributeName
     * @return
     */
    public Object getAttributeValue(String attributeName) {
        return getAttribute(attributeName).orElse(null);
    }

    /**
     * @param attributeName
     * @return
     */
    public String getAttributeValueAsString(String attributeName) {
        return getAttribute(attributeName).map(Object::toString).orElse(null);
    }

    /**
     * @param attributeName
     * @return
     */
    public Boolean getAttributeValueAsBoolean(String attributeName) {
        return getAttribute(attributeName).map(val -> Boolean.parseBoolean(val.toString())).orElse(null);
    }

    public void addAttributeValue(String attributeName, Object value) {
        attributes.put(attributeName, value);
    }

    private Optional<Object> getAttribute(String attributeName) {
        return Optional.ofNullable(attributes.get(attributeName));
    }
}
