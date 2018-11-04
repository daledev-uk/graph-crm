package com.daledev.graphcrm.api.domain;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.voodoodyne.jackson.jsog.JSOGGenerator;
import org.neo4j.ogm.annotation.*;

import java.util.Date;

import static org.neo4j.ogm.annotation.Relationship.INCOMING;

/**
 * @author dale.ellis
 * @since 05/10/2018
 */
@JsonIdentityInfo(generator = JSOGGenerator.class)
@NodeEntity
public class EntityRelationshipDefinition {
    @Id
    @GeneratedValue
    private Long id;
    @Index(unique = true)
    private String name;
    private String friendlyName;
    private String description;
    private boolean systemRelationship;
    private Date createTime;
    private Date lastUpdateTime;

    @Relationship(type = "RELATIONSHIP", direction = INCOMING)
    private FieldDefinition relationshipFrom;

    @Relationship(type = "GOES_TO")
    private EntityDefinition relationshipTo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public boolean isSystemRelationship() {
        return systemRelationship;
    }

    public void setSystemRelationship(boolean systemRelationship) {
        this.systemRelationship = systemRelationship;
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

    public FieldDefinition getRelationshipFrom() {
        return relationshipFrom;
    }

    public void setRelationshipFrom(FieldDefinition relationshipFrom) {
        this.relationshipFrom = relationshipFrom;
    }

    public EntityDefinition getRelationshipTo() {
        return relationshipTo;
    }

    public void setRelationshipTo(EntityDefinition relationshipTo) {
        this.relationshipTo = relationshipTo;
    }

    /**
     * Returns true if the relationship type is allowed to go to the given entity definition
     *
     * @param entityDefinition
     * @return
     */
    public boolean isEntityAllowed(EntityDefinition entityDefinition) {
        return relationshipTo.equals(entityDefinition);
    }
}
