package com.daledev.graphcrm.api.rest.converter;

import com.daledev.graphcrm.api.constants.SearchLogicalOperator;
import com.daledev.graphcrm.api.constants.SearchOperator;
import com.daledev.graphcrm.api.dto.search.SearchCriteriaDto;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.InitBinder;

import java.beans.PropertyEditorSupport;
import java.io.IOException;

/**
 * @author dale.ellis
 * @since 21/10/2018
 */
@ControllerAdvice
public class ParameterConverter {
    private ObjectMapper objectMapper = new ObjectMapper();

    /**
     * Registers come customer editors
     *
     * @param dataBinder
     */
    @InitBinder
    public void initBinder(WebDataBinder dataBinder) {
        dataBinder.registerCustomEditor(SearchCriteriaDto.class, new CriterionPropertyEditorSupport());
    }

    private class CriterionPropertyEditorSupport extends PropertyEditorSupport {
        Object value;

        @Override
        public Object getValue() {
            return value;
        }

        @Override
        public void setAsText(String text) {
            try {
                SearchCriteriaDto rootCriteria = new SearchCriteriaDto();
                JsonNode rootJsonNode = objectMapper.readTree(text);
                populateCriteria(rootJsonNode, rootCriteria);
                value = rootCriteria;
            } catch (IOException e) {

            }
        }

        private void populateCriteria(JsonNode jsonNode, SearchCriteriaDto searchCriteria) {
            searchCriteria.setField(getTextValue(jsonNode, "field"));
            searchCriteria.setOperator(getEnumValue(jsonNode, "operator", SearchOperator.class));
            searchCriteria.setLogicalOperator(getEnumValue(jsonNode, "logicalOperator", SearchLogicalOperator.class));
            searchCriteria.setValue(getObjectValue(jsonNode, "value"));
            if (jsonNode.has("groupedCriteria")) {
                for (JsonNode groupedCriteria : jsonNode.get("groupedCriteria")) {
                    SearchCriteriaDto childCriteria = new SearchCriteriaDto();
                    populateCriteria(groupedCriteria, childCriteria);
                    searchCriteria.getGroupedCriteria().add(childCriteria);
                }
            }
        }

        private Object getObjectValue(JsonNode jsonNode, String attributeName) {
            if (jsonNode.has(attributeName)) {
                JsonNode attributeNode = jsonNode.get(attributeName);
                if (attributeNode != null) {
                    if (attributeNode.isDouble()) {
                        return attributeNode.asDouble();
                    } else if (attributeNode.isLong()) {
                        return attributeNode.asLong();
                    } else if (attributeNode.isBoolean()) {
                        return attributeNode.asBoolean();
                    } else {
                        return attributeNode.asText();

                    }
                }
            }
            return null;
        }

        private String getTextValue(JsonNode jsonNode, String attributeName) {
            if (jsonNode.has(attributeName)) {
                JsonNode attributeNode = jsonNode.get(attributeName);
                if (attributeNode != null) {
                    return attributeNode.asText();
                }
            }
            return null;
        }

        private <E extends Enum> E getEnumValue(JsonNode jsonNode, String attributeName, Class<E> enumClass) {
            String value = getTextValue(jsonNode, attributeName);
            if (value != null) {
                return (E) Enum.valueOf(enumClass, value.toUpperCase());
            }
            return null;
        }
    }
}
