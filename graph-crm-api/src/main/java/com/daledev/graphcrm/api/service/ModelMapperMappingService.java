package com.daledev.graphcrm.api.service;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

/**
 * @author dale.ellis
 * @since 30/09/2018
 */
@Service
public class ModelMapperMappingService implements MappingService {
    private ModelMapper modelMapper = new ModelMapper();

    @Override
    public <T> T map(Object source, Class<T> destinationClass) {
        return modelMapper.map(source, destinationClass);
    }
}
