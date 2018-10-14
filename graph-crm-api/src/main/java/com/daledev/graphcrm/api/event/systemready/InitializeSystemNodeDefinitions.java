package com.daledev.graphcrm.api.event.systemready;

import com.daledev.graphcrm.api.service.InitializerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * @author dale.ellis
 * @since 05/10/2018
 */
@Component
public class InitializeSystemNodeDefinitions {
    private static final Logger log = LoggerFactory.getLogger(InitializeSystemNodeDefinitions.class);
    private InitializerService initializerService;

    /**
     * @param initialzerService
     */
    public InitializeSystemNodeDefinitions(InitializerService initialzerService) {
        this.initializerService = initialzerService;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void setupNodes() {
        log.debug("Setup System Entity and Relationships;");
        initializerService.createSystemData();
    }
}
