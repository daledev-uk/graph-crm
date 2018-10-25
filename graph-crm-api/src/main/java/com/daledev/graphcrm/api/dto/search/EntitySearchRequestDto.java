package com.daledev.graphcrm.api.dto.search;

/**
 * @author dale.ellis
 * @since 16/10/2018
 */
public class EntitySearchRequestDto extends SearchRequestDto {
    private String entityType;

    public String getEntityType() {
        return entityType;
    }

    public void setEntityType(String entityType) {
        this.entityType = entityType;
    }
}
