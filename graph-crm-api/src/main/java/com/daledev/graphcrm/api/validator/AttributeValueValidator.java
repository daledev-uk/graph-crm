package com.daledev.graphcrm.api.validator;

/**
 * @author dale.ellis
 * @since 08/10/2018
 */
public interface AttributeValueValidator {

    /**
     * Validates teh given value is the correct data type
     *
     * @param value
     */
    void validateValue(Object value);
}
