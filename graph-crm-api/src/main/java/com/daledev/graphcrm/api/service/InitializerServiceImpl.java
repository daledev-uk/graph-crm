package com.daledev.graphcrm.api.service;

import com.daledev.graphcrm.api.constants.DataType;
import com.daledev.graphcrm.api.constants.SystemEntities;
import com.daledev.graphcrm.api.domain.EntityDefinition;
import com.daledev.graphcrm.api.repository.EntityDefinitionRepository;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author dale.ellis
 * @since 07/10/2018
 */
@Service
public class InitializerServiceImpl implements InitializerService {
    private EntityDefinitionRepository entityDefinitionRepository;

    /**
     * @param entityDefinitionRepository
     */
    public InitializerServiceImpl(EntityDefinitionRepository entityDefinitionRepository) {
        this.entityDefinitionRepository = entityDefinitionRepository;
    }

    @Override
    public void createSystemData() {
        createPersonEntityDefinition();
    }

    private void createPersonEntityDefinition() {
        if (!hasEntityDefinitionBeenInitialized(SystemEntities.PERSON)) {
            EntityDefinition personEntityDefinition = createEntityDefinition(SystemEntities.PERSON.getFriendlyName(), "Entity which represent an individual person");
            personEntityDefinition.addField("title", "Persons Title, Mrs, Ms Mr etc...", DataType.TEXT, personEntityDefinition.getCreateTime(), false, true);
            personEntityDefinition.addField("firstName", "Persons First Name", DataType.TEXT, personEntityDefinition.getCreateTime(), true, true);
            personEntityDefinition.addField("lastName", "Persons Last Name", DataType.TEXT, personEntityDefinition.getCreateTime(), true, true);
            personEntityDefinition.addField("dateOfBirth", "Date person was born", DataType.DATE, personEntityDefinition.getCreateTime(), false, true);
            personEntityDefinition.addField("gender", "MALE, FEMALE or UNSPECIFIED", DataType.TEXT, personEntityDefinition.getCreateTime(), false, true);
            entityDefinitionRepository.save(personEntityDefinition);
        }
    }

    private boolean hasEntityDefinitionBeenInitialized(SystemEntities name) {
        return entityDefinitionRepository.getByName(name.getFriendlyName()) != null;
    }

    private EntityDefinition createEntityDefinition(String name, String description) {
        EntityDefinition entityDefinition = new EntityDefinition();
        entityDefinition.setName(name);
        entityDefinition.setDescription(description);
        entityDefinition.setSystemEntity(true);
        Date date = new Date();
        entityDefinition.setCreateTime(date);
        entityDefinition.setLastUpdateTime(date);
        return entityDefinition;
    }
}
