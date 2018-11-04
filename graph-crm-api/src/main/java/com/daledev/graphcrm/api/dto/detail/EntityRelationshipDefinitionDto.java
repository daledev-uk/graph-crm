package com.daledev.graphcrm.api.dto.detail;

/**
 * @author dale.ellis
 * @since 11/10/2018
 */
public class EntityRelationshipDefinitionDto {
    private String name;
    private String friendlyName;
    private String description;
    private String canRelatedTo;

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

    public String getCanRelatedTo() {
        return canRelatedTo;
    }

    public void setCanRelatedTo(String canRelatedTo) {
        this.canRelatedTo = canRelatedTo;
    }
}
