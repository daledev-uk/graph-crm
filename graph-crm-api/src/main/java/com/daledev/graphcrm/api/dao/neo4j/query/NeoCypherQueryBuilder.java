package com.daledev.graphcrm.api.dao.neo4j.query;

import com.daledev.graphcrm.api.constants.SearchLogicalOperator;
import com.daledev.graphcrm.api.constants.SearchOperator;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author dale.ellis
 * @since 18/10/2018
 */
public class NeoCypherQueryBuilder {
    private int paramCount = 0;

    private StringBuilder cypher = new StringBuilder();
    private StringBuilder whereClause = new StringBuilder();
    private StringBuilder returnClause = new StringBuilder();
    private StringBuilder pagingClause = new StringBuilder();
    private Map<String, Object> bindParameters = new LinkedHashMap<>();

    private List<StringBuilder> openSubConditionSyntax = new ArrayList<>();
    private List<SearchLogicalOperator> openSubConditions = new ArrayList<>();


    /**
     *
     */
    public NeoCypherQueryBuilder(QueryType queryType) {
        cypher.append(queryType).append(" ");
        openSubConditionSyntax.add(whereClause);
        openSubConditions.add(SearchLogicalOperator.AND);
    }

    public NeoCypherQueryBuilder nodeWithLabel(String label, String alias) {
        cypher.append(String.format("(%s:%s)", alias, label));
        return this;
    }

    public NeoCypherQueryBuilder nodeWithLabelWithParameters(String label, String alias, Parameter... parameters) {
        if (parameters.length == 0) {
            return nodeWithLabel(label, alias);
        }

        String parameterSyntax = getParameterSyntax(parameters);
        cypher.append(String.format("(%s:%s %s)", alias, label, parameterSyntax));
        return this;
    }

    public NeoCypherQueryBuilder anyNode(String alias) {
        cypher.append(String.format("(%s)", alias));
        return this;
    }

    public NeoCypherQueryBuilder anyNodeWithParameters(String alias, Parameter... parameters) {
        if (parameters.length == 0) {
            return anyNode(alias);
        }

        String parameterSyntax = getParameterSyntax(parameters);
        cypher.append(String.format("(%s %s)", alias, parameterSyntax));

        return this;
    }

    public NeoCypherQueryBuilder anyRelationshipFrom(String alias) {
        cypher.append(String.format("-[%s]->", alias));
        return this;
    }

    public NeoCypherQueryBuilder anyRelationshipTo(String alias) {
        cypher.append(String.format("<-[%s]-", alias));
        return this;
    }

    public NeoCypherQueryBuilder relationshipFromWithLabel(String label, String alias) {
        cypher.append(String.format("-[%s:%s]->", alias, label));
        return this;
    }

    public NeoCypherQueryBuilder relationshipToWithLabel(String label, String alias) {
        cypher.append(String.format("<-[%s:%s]-", alias, label));
        return this;
    }

    public NeoCypherQueryBuilder addWhereCondition(String fieldAlias, String field, SearchOperator operator, Object value) {
        String bindVariableName = field + "_" + (paramCount++);
        StringBuilder currentCondition = getCurrentCondition();

        if (currentCondition.length() > 0) {
            currentCondition.append(" ").append(getCurrentConditionLogicalOperator()).append(" ");
        }

        currentCondition.append(fieldAlias).append(".").append(field).append(" ").append(getOperatorSyntax(operator)).append(" ");
        currentCondition.append(getBindParameterSyntax(bindVariableName));
        bindParameters.put(bindVariableName, value);
        return this;
    }

    public NeoCypherQueryBuilder setCurrentConditionLogicalOperator(SearchLogicalOperator logicalOperator) {
        openSubConditions.set(openSubConditions.size() - 1, logicalOperator);
        return this;
    }

    public NeoCypherQueryBuilder orSubCondition() {
        return subCondition(SearchLogicalOperator.OR);
    }

    public NeoCypherQueryBuilder andSubCondition() {
        return subCondition(SearchLogicalOperator.AND);
    }

    public NeoCypherQueryBuilder subCondition(SearchLogicalOperator logicalOperator) {
        openSubConditions.add(logicalOperator);
        openSubConditionSyntax.add(new StringBuilder());
        return this;
    }

    public NeoCypherQueryBuilder endSubCondition() {
        if (openSubConditions.size() > 1) {
            openSubConditions.remove(openSubConditions.size() - 1);
            StringBuilder currentCondition = openSubConditionSyntax.remove(openSubConditionSyntax.size() - 1);

            if (currentCondition.length() > 0) {
                currentCondition.insert(0, "(");
                currentCondition.append(")");

                if (whereClause.length() > 0) {
                    whereClause.append(" ").append(getCurrentConditionLogicalOperator()).append(" ");
                }
                whereClause.append(currentCondition);
            }
        }
        return this;
    }

    public NeoCypherQueryBuilder returnNodes(String... nodesToReturn) {
        String nodesToReturnAsCsv = Stream.of(nodesToReturn).collect(Collectors.joining(", "));

        if (returnClause.length() == 0) {
            returnClause.append(" RETURN ");
        }

        returnClause.append(nodesToReturnAsCsv);
        return this;
    }

    public NeoCypherQueryBuilder skipFirstNumberOfResults(Integer startIndex) {
        if (startIndex != null) {
            pagingClause.append(" SKIP ").append(startIndex);
        }
        return this;
    }

    public NeoCypherQueryBuilder resultLimit(Integer pageSize) {
        if (pageSize != null) {
            pagingClause.append(" LIMIT ").append(pageSize);
        }
        return this;
    }

    public NeoCypherQuery build() {
        final String finalWhere = getWhereSyntax();
        final String finalCypher = cypher.toString() + finalWhere + returnClause + pagingClause;
        final String finalCountCypher = cypher.toString() + finalWhere + " RETURN count(e)";

        return new NeoCypherQuery(finalCypher, finalCountCypher, bindParameters);
    }

    private String getWhereSyntax() {
        while (openSubConditions.size() > 1) {
            endSubCondition();
        }

        if (whereClause.length() > 0) {
            whereClause.insert(0, " WHERE ");
        }

        return whereClause.toString();
    }

    private StringBuilder getCurrentCondition() {
        if (!openSubConditionSyntax.isEmpty()) {
            return openSubConditionSyntax.get(openSubConditionSyntax.size() - 1);
        }
        return null;
    }

    private StringBuilder popCondition() {
        if (!openSubConditionSyntax.isEmpty()) {
            return openSubConditionSyntax.remove(0);
        }
        return null;
    }

    private SearchLogicalOperator getCurrentConditionLogicalOperator() {
        return openSubConditions.get(openSubConditions.size() - 1);
    }


    private String getParameterSyntax(Parameter... parameters) {
        StringJoiner paramSyntaxEntries = new StringJoiner(", ");
        for (Parameter parameter : parameters) {
            String bindVariableName = parameter.getParameterName() + "_" + (paramCount++);
            paramSyntaxEntries.add(String.format("%s: %s", parameter.getParameterName(), getBindParameterSyntax(bindVariableName)));
            bindParameters.put(bindVariableName, parameter.getValue());
        }

        return "{" + paramSyntaxEntries.toString() + "}";
    }

    private String getBindParameterSyntax(String parameterName) {
        return String.format("$%s", parameterName);
    }

    private String getOperatorSyntax(SearchOperator operator) {
        switch (operator) {
            case EQUALS:
                return "=";
            case LESS_THAN:
                return "<";
            case LESS_THAN_OR_EQUAL:
                return "<=";
            case GREATER_THAN:
                return ">";
            case GREATER_THAN_OR_EQUAL:
                return ">=";
            default:
                return operator.name();
        }
    }
}
