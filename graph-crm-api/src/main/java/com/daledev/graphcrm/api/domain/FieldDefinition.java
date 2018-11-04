package com.daledev.graphcrm.api.domain;

import com.daledev.graphcrm.api.constants.DataType;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.voodoodyne.jackson.jsog.JSOGGenerator;
import org.neo4j.ogm.annotation.*;

import java.util.Date;

import static org.neo4j.ogm.annotation.Relationship.OUTGOING;

/**
 * @author dale.ellis
 * @since 05/10/2018
 */
@JsonIdentityInfo(generator = JSOGGenerator.class)
@NodeEntity
public class FieldDefinition {
    @Id
    @GeneratedValue
    private Long id;
    @Index
    private String name;
    private String description;
    private DataType dataType;
    private String template;
    private boolean mandatory;
    private boolean systemField;
    private Date createTime;
    private Date lastUpdateTime;
    @Relationship(type = "RELATIONSHIP", direction = OUTGOING)
    private EntityRelationshipDefinition relationshipDefinition;

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

    public DataType getDataType() {
        return dataType;
    }

    public void setDataType(DataType dataType) {
        this.dataType = dataType;
    }

    public boolean isFieldRepresentingRelationship() {
        return dataType == DataType.RELATIONSHIP;
    }

    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }

    public boolean isMandatory() {
        return mandatory;
    }

    public void setMandatory(boolean mandatory) {
        this.mandatory = mandatory;
    }

    public boolean isSystemField() {
        return systemField;
    }

    public void setSystemField(boolean systemField) {
        this.systemField = systemField;
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

    public EntityRelationshipDefinition getRelationshipDefinition() {
        return relationshipDefinition;
    }

    public void setRelationshipDefinition(EntityRelationshipDefinition relationshipDefinition) {
        this.relationshipDefinition = relationshipDefinition;
    }
}
