package com.daledev.graphcrm.api.config;

import org.neo4j.ogm.session.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;
import org.springframework.data.neo4j.transaction.Neo4jTransactionManager;

/**
 * @author dale.ellis
 * @since 30/09/2018
 */
@Configuration
@EnableNeo4jRepositories(basePackages = "com.daledev.graphcrm.api.repository")
@Profile("test")
public class Neo4jCoreDatabaseTestConfiguration {
    @Bean
    public org.neo4j.ogm.config.Configuration getConfiguration() {
        return new org.neo4j.ogm.config.Configuration.Builder().build();
    }

    @Bean
    public SessionFactory sessionFactory() {
        return new SessionFactory(getConfiguration(), "com.daledev.graphcrm.api.domain");
    }

    @Bean
    public Neo4jTransactionManager transactionManager() {
        return new Neo4jTransactionManager(sessionFactory());
    }
}
