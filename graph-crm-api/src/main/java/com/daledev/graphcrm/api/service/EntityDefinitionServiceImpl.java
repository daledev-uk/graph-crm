package com.daledev.graphcrm.api.service;

import com.daledev.graphcrm.api.domain.EntityDefinition;
import com.daledev.graphcrm.api.domain.EntityRelationshipDefinition;
import com.daledev.graphcrm.api.domain.FieldDefinition;
import com.daledev.graphcrm.api.dto.detail.FieldDefinitionDto;
import com.daledev.graphcrm.api.dto.request.CreateEntityDefinitionRequestDto;
import com.daledev.graphcrm.api.dto.request.CreateEntityRequestDto;
import com.daledev.graphcrm.api.dto.request.UpdateEntityDefinitionRequestDto;
import com.daledev.graphcrm.api.exception.EntityDefinitionAlreadyExists;
import com.daledev.graphcrm.api.exception.EntityDefinitionFieldNotFound;
import com.daledev.graphcrm.api.exception.EntityDefinitionNotFound;
import com.daledev.graphcrm.api.exception.MandatoryEntityDefinitionFieldNotPopulated;
import com.daledev.graphcrm.api.repository.EntityDefinitionRepository;
import com.daledev.graphcrm.api.repository.EntityRelationshipDefinitionRepository;
import com.daledev.graphcrm.api.validator.AttributeValueValidator;
import com.daledev.graphcrm.api.validator.AttributeValueValidatorFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Map;
import java.util.Optional;

/**
 * @author dale.ellis
 * @since 07/10/2018
 */
@Service
@Transactional
public class EntityDefinitionServiceImpl implements EntityDefinitionService {
    private EntityDefinitionRepository entityDefinitionRepository;
    private EntityRelationshipDefinitionRepository entityRelationshipDefinitionRepository;

    /**
     * @param entityDefinitionRepository
     * @param entityRelationshipDefinitionRepository
     */
    public EntityDefinitionServiceImpl(EntityDefinitionRepository entityDefinitionRepository, EntityRelationshipDefinitionRepository entityRelationshipDefinitionRepository) {
        this.entityDefinitionRepository = entityDefinitionRepository;
        this.entityRelationshipDefinitionRepository = entityRelationshipDefinitionRepository;
    }

    @Override
    public EntityDefinition validateEntity(CreateEntityRequestDto request) {
        EntityDefinition entityDefinition = getEntityDefinitionByName(request.getEntityType());
        validateFieldValues(request.getValues(), entityDefinition);
        validateMandatoryFieldsPopulated(request.getValues(), entityDefinition);
        return entityDefinition;
    }

    @Override
    public void createEntityDefinition(CreateEntityDefinitionRequestDto createRequest) {
        if (entityDefinitionRepository.getByName(createRequest.getName()) != null) {
            throw new EntityDefinitionAlreadyExists(createRequest.getName());
        }

        EntityDefinition entityDefinition = new EntityDefinition();
        entityDefinition.setName(createRequest.getName());
        entityDefinition.setDescription(createRequest.getDescription());
        entityDefinition.setCreateTime(new Date());
        entityDefinition.setLastUpdateTime(new Date());

        for (FieldDefinitionDto fieldDefinitionToCreate : createRequest.getFields()) {
            populateEntityField(entityDefinition, fieldDefinitionToCreate);
        }

        entityDefinitionRepository.save(entityDefinition);
    }

    @Override
    public void updateEntityDefinition(UpdateEntityDefinitionRequestDto updateRequest) {
        EntityDefinition entityDefinition = entityDefinitionRepository.getByName(updateRequest.getName());
        if (entityDefinition == null) {
            throw new EntityDefinitionNotFound(updateRequest.getName());
        }
        entityDefinition.setDescription(updateRequest.getDescription());
        entityDefinition.setLastUpdateTime(new Date());
        for (FieldDefinitionDto fieldDefinitionDto : updateRequest.getFields()) {
            Optional<FieldDefinition> field = entityDefinition.getFieldByName(fieldDefinitionDto.getName());
            if (!field.isPresent()) {
                populateEntityField(entityDefinition, fieldDefinitionDto);
            }
        }
        entityDefinitionRepository.save(entityDefinition);
    }

    private EntityDefinition getEntityDefinitionByName(String entityType) {
        EntityDefinition entityDefinition = entityDefinitionRepository.getByName(entityType);
        if (entityDefinition == null) {
            throw new EntityDefinitionNotFound(entityType);
        }
        return entityDefinition;
    }

    private void populateEntityField(EntityDefinition entityDefinition, FieldDefinitionDto fieldDefinitionToCreate) {
        FieldDefinition fieldDefinition = entityDefinition.addField(fieldDefinitionToCreate.getName(), fieldDefinitionToCreate.getDescription(), fieldDefinitionToCreate.getDataType(), new Date(), fieldDefinitionToCreate.isMandatory(), false);
        fieldDefinition.setTemplate(fieldDefinitionToCreate.getTemplate());
        if (fieldDefinition.isFieldRepresentingRelationship()) {
            populateEntityRelationship(fieldDefinition, fieldDefinitionToCreate);
        }
    }

    private void populateEntityRelationship(FieldDefinition fieldDefinition, FieldDefinitionDto fieldDefinitionToCreate) {
        EntityRelationshipDefinition representedRelationship = new EntityRelationshipDefinition();
        representedRelationship.setName(fieldDefinitionToCreate.getRepresentedRelationship().getName());
        representedRelationship.setFriendlyName(fieldDefinitionToCreate.getRepresentedRelationship().getFriendlyName());
        representedRelationship.setDescription(fieldDefinitionToCreate.getRepresentedRelationship().getDescription());
        representedRelationship.setCreateTime(new Date());
        representedRelationship.setLastUpdateTime(new Date());

        representedRelationship.setRelationshipFrom(fieldDefinition);
        fieldDefinition.setRelationshipDefinition(representedRelationship);
        representedRelationship.setRelationshipTo(getEntityDefinitionByName(fieldDefinitionToCreate.getRepresentedRelationship().getCanRelatedTo()));
        
        entityRelationshipDefinitionRepository.save(representedRelationship);
    }

    private void validateFieldValues(Map<String, Object> values, EntityDefinition entityDefinition) {
        values.forEach((fieldName, value) -> validateFieldValue(fieldName, value, entityDefinition));
    }

    private void validateFieldValue(String fieldName, Object value, EntityDefinition entityDefinition) {
        Optional<FieldDefinition> fieldByName = entityDefinition.getFieldByName(fieldName);
        FieldDefinition fieldDefinition = fieldByName.orElseThrow(() -> new EntityDefinitionFieldNotFound(entityDefinition, fieldName));
        validateFieldValuesDataType(value, fieldDefinition);
    }

    private void validateFieldValuesDataType(Object value, FieldDefinition fieldDefinition) {
        AttributeValueValidator validator = AttributeValueValidatorFactory.getValidator(fieldDefinition.getDataType());
        validator.validateValue(value);
    }

    private void validateMandatoryFieldsPopulated(Map<String, Object> values, EntityDefinition entityDefinition) {
        for (FieldDefinition fieldDefinition : entityDefinition.getFields()) {
            if (fieldDefinition.isMandatory()) {
                validateMandatoryFieldPopulated(entityDefinition, fieldDefinition.getName(), values);
            }
        }
    }

    private void validateMandatoryFieldPopulated(EntityDefinition entityDefinition, String fieldName, Map<String, Object> values) {
        if (values.get(fieldName) == null) {
            throw new MandatoryEntityDefinitionFieldNotPopulated(entityDefinition, fieldName);
        }
    }
}
