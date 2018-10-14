package com.daledev.graphcrm.api.validator;

import com.daledev.graphcrm.api.constants.DataType;

/**
 * @author dale.ellis
 * @since 08/10/2018
 */
public class AttributeValueValidatorFactory {

    private AttributeValueValidatorFactory() {
    }

    /**
     * @param dataType
     * @return
     */
    public static AttributeValueValidator getValidator(DataType dataType) {
        switch (dataType) {
            case TEXT:
                return new TextAttributeValidator();
            case BOOLEAN:
                return new BooleanAttributeValidator();
            case DATE:
                return new DateAttributeValidator();
            case NUMBER:
                return new NumberAttributeValidator();
            case RELATIONSHIP:
                return new RelationshipAttributeValidator();
            default:
                return new TextAttributeValidator();
        }
    }
}
