package com.daledev.graphcrm.api.config;

import org.neo4j.ogm.session.SessionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;
import org.springframework.data.neo4j.transaction.Neo4jTransactionManager;

/**
 * @author dale.ellis
 * @since 27/09/2018
 */
@Configuration
@EnableNeo4jRepositories(basePackages = "com.daledev.graphcrm.api.repository")
public class Neo4jCoreDatabaseConfiguration {
    @Value("${core.neo4j.url:http://neo4j:password@localhost:7474}")
    private String coreDatabaseUrl;

    @Bean
    public org.neo4j.ogm.config.Configuration getConfiguration() {
        return new org.neo4j.ogm.config.Configuration.Builder().uri(coreDatabaseUrl).build();
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
