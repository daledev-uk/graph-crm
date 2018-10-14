package com.daledev.graphcrm.api.rest.controller;

import com.daledev.graphcrm.api.config.Neo4jCoreDatabaseTestConfiguration;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author dale.ellis
 * @since 30/09/2018
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = Neo4jCoreDatabaseTestConfiguration.class)
@ActiveProfiles(profiles = "test")
public class PersonControllerTest {

    @Test
    public void getPerson() {
    }

    @Test
    public void createPerson() {
    }

    @Test
    public void updatePerson() {
    }
}