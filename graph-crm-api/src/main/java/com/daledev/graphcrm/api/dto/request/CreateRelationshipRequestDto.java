package com.daledev.graphcrm.api.dto.request;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author dale.ellis
 * @since 02/10/2018
 */
public class CreateRelationshipRequestDto implements Serializable {
    private String fromEntityId;
    private String toEntityId;
    private String relationshipType;
    private Map<String, Object> relationshipAttributes = new LinkedHashMap<>();

    public String getFromEntityId() {
        return fromEntityId;
    }

    public void setFromEntityId(String fromEntityId) {
        this.fromEntityId = fromEntityId;
    }

    public String getToEntityId() {
        return toEntityId;
    }

    public void setToEntityId(String toEntityId) {
        this.toEntityId = toEntityId;
    }

    public String getRelationshipType() {
        return relationshipType;
    }

    public void setRelationshipType(String relationshipType) {
        this.relationshipType = relationshipType;
    }

    public Map<String, Object> getRelationshipAttributes() {
        return relationshipAttributes;
    }

    public void setRelationshipAttributes(Map<String, Object> relationshipAttributes) {
        this.relationshipAttributes = relationshipAttributes;
    }
}
