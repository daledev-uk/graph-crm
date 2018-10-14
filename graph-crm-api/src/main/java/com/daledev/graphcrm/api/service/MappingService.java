package com.daledev.graphcrm.api.service;

/**
 * @author dale.ellis
 * @since 30/09/2018
 */
public interface MappingService {

    /**
     * @param source
     * @param destinationClass
     * @param <T>
     * @return
     */
    <T> T map(Object source, Class<T> destinationClass);
}
