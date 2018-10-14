package com.daledev.graphcrm.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author dale.ellis
 * @since 30/09/2018
 */
@SpringBootApplication
public class GraphCrmCoreApiApplication {
    private static final Logger log = LoggerFactory.getLogger(GraphCrmCoreApiApplication.class);

    /**
     * Starts the Spring boot application
     *
     * @param args
     */
    public static void main(String[] args) {
        log.debug("Application started");
        SpringApplication.run(GraphCrmCoreApiApplication.class, args);
    }
}
