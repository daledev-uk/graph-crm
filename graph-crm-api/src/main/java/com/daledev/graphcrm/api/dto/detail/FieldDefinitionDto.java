package com.daledev.graphcrm.api.dto.detail;

import com.daledev.graphcrm.api.constants.DataType;

/**
 * @author dale.ellis
 * @since 11/10/2018
 */
public class FieldDefinitionDto {
    private String name;
    private String description;
    private DataType dataType;
    private boolean mandatory;
    private EntityRelationshipDefinitionDto representedRelationship;

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

    public DataType getDataType() {
        return dataType;
    }

    public void setDataType(DataType dataType) {
        this.dataType = dataType;
    }

    public boolean isMandatory() {
        return mandatory;
    }

    public void setMandatory(boolean mandatory) {
        this.mandatory = mandatory;
    }

    public EntityRelationshipDefinitionDto getRepresentedRelationship() {
        return representedRelationship;
    }

    public void setRepresentedRelationship(EntityRelationshipDefinitionDto representedRelationship) {
        this.representedRelationship = representedRelationship;
    }
}
