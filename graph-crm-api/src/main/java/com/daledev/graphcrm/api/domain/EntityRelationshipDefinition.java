package com.daledev.graphcrm.api.domain;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.voodoodyne.jackson.jsog.JSOGGenerator;
import org.neo4j.ogm.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

    @Relationship(type = "RELATIONSHIP_GOING_FROM")
    private EntityDefinition relationshipFrom;

    @Relationship(type = "RELATIONSHIP_GOING_TO")
    private List<EntityDefinition> relationshipTo = new ArrayList<>();

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

    public EntityDefinition getRelationshipFrom() {
        return relationshipFrom;
    }

    public void setRelationshipFrom(EntityDefinition relationshipFrom) {
        this.relationshipFrom = relationshipFrom;
    }

    public List<EntityDefinition> getRelationshipTo() {
        return relationshipTo;
    }

    public void setRelationshipTo(List<EntityDefinition> relationshipTo) {
        this.relationshipTo = relationshipTo;
    }

    /**
     * Returns true if the relationship type is allowed to go to the given entity definition
     *
     * @param entityDefinition
     * @return
     */
    public boolean isEntityAllowed(EntityDefinition entityDefinition) {
        return relationshipTo.contains(entityDefinition);
    }
}
