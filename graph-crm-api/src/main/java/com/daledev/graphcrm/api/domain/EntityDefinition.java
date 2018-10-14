package com.daledev.graphcrm.api.domain;

import com.daledev.graphcrm.api.constants.DataType;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.voodoodyne.jackson.jsog.JSOGGenerator;
import org.neo4j.ogm.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * @author dale.ellis
 * @since 05/10/2018
 */
@JsonIdentityInfo(generator = JSOGGenerator.class)
@NodeEntity
public class EntityDefinition {
    @Id
    @GeneratedValue
    private Long id;
    @Index(unique = true)
    private String name;
    private String description;
    private boolean systemEntity;
    private Date createTime;
    private Date lastUpdateTime;

    @Relationship(type = "FIELD")
    private List<FieldDefinition> fields = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public boolean isSystemEntity() {
        return systemEntity;
    }

    public void setSystemEntity(boolean systemEntity) {
        this.systemEntity = systemEntity;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(Date lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    public List<FieldDefinition> getFields() {
        return fields;
    }

    public void setFields(List<FieldDefinition> fields) {
        this.fields = fields;
    }

    /**
     * Gets field definition by name
     *
     * @param fieldName
     * @return
     */
    public Optional<FieldDefinition> getFieldByName(String fieldName) {
        for (FieldDefinition field : fields) {
            if (field.getName().equals(fieldName)) {
                return Optional.of(field);
            }
        }
        return Optional.empty();
    }

    /**
     * @param name
     * @param description
     * @param dataType
     * @param createdTime
     * @param systemField
     * @return
     */
    public FieldDefinition addField(String name, String description, DataType dataType, Date createdTime, boolean mandatory, boolean systemField) {
        FieldDefinition fieldDefinition = new FieldDefinition();
        fieldDefinition.setName(name);
        fieldDefinition.setDescription(description);
        fieldDefinition.setDataType(dataType);
        fieldDefinition.setCreateTime(createdTime);
        fieldDefinition.setLastUpdateTime(createdTime);
        fieldDefinition.setSystemField(systemField);
        fields.add(fieldDefinition);
        return fieldDefinition;
    }
}
