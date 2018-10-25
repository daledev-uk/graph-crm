package com.daledev.graphcrm.api.dto.request;

import com.daledev.graphcrm.api.dto.detail.FieldDefinitionDto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author dale.ellis
 * @since 18/10/2018
 */
public class UpdateEntityDefinitionRequestDto implements Serializable {
    private String name;
    private String description;
    private List<FieldDefinitionDto> fields = new ArrayList<>();

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

    public List<FieldDefinitionDto> getFields() {
        return fields;
    }

    public void setFields(List<FieldDefinitionDto> fields) {
        this.fields = fields;
    }
}
