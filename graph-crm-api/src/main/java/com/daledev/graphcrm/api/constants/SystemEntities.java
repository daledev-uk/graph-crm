package com.daledev.graphcrm.api.constants;

/**
 * @author dale.ellis
 * @since 07/10/2018
 */
public enum SystemEntities {
    PERSON("Person");

    private String friendlyName;

    SystemEntities(String friendlyName) {
        this.friendlyName = friendlyName;
    }

    public String getFriendlyName() {
        return friendlyName;
    }
}
