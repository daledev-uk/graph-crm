package com.daledev.graphcrm.api.dto.detail;

import java.util.ArrayList;
import java.util.List;

/**
 * @author dale.ellis
 * @since 11/10/2018
 */
public class EntityRelationshipDefinitionDto {
    private String name;
    private String friendlyName;
    private String description;
    private List<String> canRelatedTo = new ArrayList<>();

    public String getFriendlyName() {
        return friendlyName;
    }

    public void setFriendlyName(String friendlyName) {
        this.friendlyName = friendlyName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getCanRelatedTo() {
        return canRelatedTo;
    }

    public void setCanRelatedTo(List<String> canRelatedTo) {
        this.canRelatedTo = canRelatedTo;
    }
}
