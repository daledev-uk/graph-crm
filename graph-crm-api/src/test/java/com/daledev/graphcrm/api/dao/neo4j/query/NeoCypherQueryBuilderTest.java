package com.daledev.graphcrm.api.dao.neo4j.query;

import com.daledev.graphcrm.api.constants.SearchLogicalOperator;
import com.daledev.graphcrm.api.constants.SearchOperator;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

/**
 * @author dale.ellis
 * @since 18/10/2018
 */
@RunWith(Enclosed.class)
public class NeoCypherQueryBuilderTest {

    public static class NodesAndRelationships {

        @Test
        public void getAllNodes() {
            // Given
            NeoCypherQueryBuilder builder = new NeoCypherQueryBuilder(QueryType.MATCH);
            builder.anyNode("n");
            builder.returnNodes("n");

            // When
            NeoCypherQuery cypherQuery = builder.build();

            // Then
            assertEquals("MATCH (n) RETURN n", cypherQuery.getQuery());
        }

        @Test
        public void getLabelNodes() {
            // Given
            NeoCypherQueryBuilder builder = new NeoCypherQueryBuilder(QueryType.MATCH);
            builder.nodeWithLabel("Person", "p");
            builder.returnNodes("p");

            // When
            NeoCypherQuery cypherQuery = builder.build();

            // Then
            assertEquals("MATCH (p:Person) RETURN p", cypherQuery.getQuery());
        }

        @Test
        public void getAllNodesWithParameterName() {
            // Given
            NeoCypherQueryBuilder builder = new NeoCypherQueryBuilder(QueryType.MATCH);
            builder.anyNodeWithParameters("n", new Parameter("name", "Dale"));
            builder.returnNodes("n");

            // When
            NeoCypherQuery cypherQuery = builder.build();

            // Then
            assertEquals("MATCH (n {name: $name_0}) RETURN n", cypherQuery.getQuery());
            assertEquals("Dale", cypherQuery.getParameters().get("name_0"));
        }

        @Test
        public void getLabelNodesWithParameterName() {
            // Given
            NeoCypherQueryBuilder builder = new NeoCypherQueryBuilder(QueryType.MATCH);
            builder.nodeWithLabelWithParameters("Person", "p", new Parameter("firstName", "Dale"), new Parameter("lastName", "Ellis"));
            builder.returnNodes("p");

            // When
            NeoCypherQuery cypherQuery = builder.build();

            // Then
            assertEquals("MATCH (p:Person {firstName: $firstName_0, lastName: $lastName_1}) RETURN p", cypherQuery.getQuery());
            assertEquals("Dale", cypherQuery.getParameters().get("firstName_0"));
            assertEquals("Ellis", cypherQuery.getParameters().get("lastName_1"));
        }

        @Test
        public void getAllNodesWithRelationshipFromAnotherNode() {
            // Given
            NeoCypherQueryBuilder builder = new NeoCypherQueryBuilder(QueryType.MATCH);
            builder.anyNode("n").anyRelationshipTo("r").nodeWithLabel("Person", "p");
            builder.returnNodes("n", "p");

            // When
            NeoCypherQuery cypherQuery = builder.build();

            // Then
            assertEquals("MATCH (n)<-[r]-(p:Person) RETURN n, p", cypherQuery.getQuery());
        }

        @Test
        public void getAllNodesWithRelationshipToAnotherNode() {
            // Given
            NeoCypherQueryBuilder builder = new NeoCypherQueryBuilder(QueryType.MATCH);
            builder.anyNode("n").anyRelationshipFrom("r").nodeWithLabel("Person", "p");
            builder.returnNodes("n", "p");

            // When
            NeoCypherQuery cypherQuery = builder.build();

            // Then
            assertEquals("MATCH (n)-[r]->(p:Person) RETURN n, p", cypherQuery.getQuery());
        }
    }

    public static class WhereOperators {

        @Test
        public void getLabelNodesWithParameterNameAndPaging() {
            // Given
            NeoCypherQueryBuilder builder = new NeoCypherQueryBuilder(QueryType.MATCH);
            builder.nodeWithLabelWithParameters("Person", "p", new Parameter("firstName", "Dale"));
            builder.returnNodes("p");
            builder.skipFirstNumberOfResults(10);
            builder.resultLimit(50);

            // When
            NeoCypherQuery cypherQuery = builder.build();

            // Then
            assertEquals("MATCH (p:Person {firstName: $firstName_0}) RETURN p SKIP 10 LIMIT 50", cypherQuery.getQuery());
            assertEquals("Dale", cypherQuery.getParameters().get("firstName_0"));
        }

        @Test
        public void getLabelNodesWithContainsWhereCondition() {
            // Given
            NeoCypherQueryBuilder builder = new NeoCypherQueryBuilder(QueryType.MATCH);
            builder.nodeWithLabel("Person", "p");
            builder.returnNodes("p");
            builder.addWhereCondition("p", "firstName", SearchOperator.CONTAINS, "Dale");

            // When
            NeoCypherQuery cypherQuery = builder.build();

            // Then
            assertEquals("MATCH (p:Person) WHERE p.firstName CONTAINS $firstName_0 RETURN p", cypherQuery.getQuery());
            assertEquals("Dale", cypherQuery.getParameters().get("firstName_0"));
        }

        @Test
        public void getLabelNodesWithStartsWithWhereCondition() {
            // Given
            NeoCypherQueryBuilder builder = new NeoCypherQueryBuilder(QueryType.MATCH);
            builder.nodeWithLabel("Person", "p");
            builder.returnNodes("p");
            builder.addWhereCondition("p", "name", SearchOperator.STARTS_WITH, "Dale");

            // When
            NeoCypherQuery cypherQuery = builder.build();

            // Then
            assertEquals("MATCH (p:Person) WHERE p.name STARTS_WITH $name_0 RETURN p", cypherQuery.getQuery());
            assertEquals("Dale", cypherQuery.getParameters().get("name_0"));
        }

        @Test
        public void getLabelNodesWithEndsWithWhereCondition() {
            // Given
            NeoCypherQueryBuilder builder = new NeoCypherQueryBuilder(QueryType.MATCH);
            builder.nodeWithLabel("Person", "p");
            builder.returnNodes("p");
            builder.addWhereCondition("p", "name", SearchOperator.ENDS_WITH, "Ellis");

            // When
            NeoCypherQuery cypherQuery = builder.build();

            // Then
            assertEquals("MATCH (p:Person) WHERE p.name ENDS_WITH $name_0 RETURN p", cypherQuery.getQuery());
            assertEquals("Ellis", cypherQuery.getParameters().get("name_0"));
        }

        @Test
        public void getLabelNodesWithGreaterThanWhereCondition() {
            // Given
            NeoCypherQueryBuilder builder = new NeoCypherQueryBuilder(QueryType.MATCH);
            builder.nodeWithLabel("Person", "p");
            builder.returnNodes("p");
            builder.addWhereCondition("p", "age", SearchOperator.GREATER_THAN, 60);

            // When
            NeoCypherQuery cypherQuery = builder.build();

            // Then
            assertEquals("MATCH (p:Person) WHERE p.age > $age_0 RETURN p", cypherQuery.getQuery());
            assertEquals(60, cypherQuery.getParameters().get("age_0"));
        }

        @Test
        public void getLabelNodesWithGreaterOrEqualWhereCondition() {
            // Given
            NeoCypherQueryBuilder builder = new NeoCypherQueryBuilder(QueryType.MATCH);
            builder.nodeWithLabel("Person", "p");
            builder.returnNodes("p");
            builder.addWhereCondition("p", "age", SearchOperator.GREATER_THAN_OR_EQUAL, 60);

            // When
            NeoCypherQuery cypherQuery = builder.build();

            // Then
            assertEquals("MATCH (p:Person) WHERE p.age >= $age_0 RETURN p", cypherQuery.getQuery());
            assertEquals(60, cypherQuery.getParameters().get("age_0"));
        }

        @Test
        public void getLabelNodesWithLessThanWhereCondition() {
            // Given
            NeoCypherQueryBuilder builder = new NeoCypherQueryBuilder(QueryType.MATCH);
            builder.nodeWithLabel("Person", "p");
            builder.returnNodes("p");
            builder.addWhereCondition("p", "age", SearchOperator.LESS_THAN, 60);

            // When
            NeoCypherQuery cypherQuery = builder.build();

            // Then
            assertEquals("MATCH (p:Person) WHERE p.age < $age_0 RETURN p", cypherQuery.getQuery());
            assertEquals(60, cypherQuery.getParameters().get("age_0"));
        }

        @Test
        public void getLabelNodesWithLessOrEqualWhereCondition() {
            // Given
            NeoCypherQueryBuilder builder = new NeoCypherQueryBuilder(QueryType.MATCH);
            builder.nodeWithLabel("Person", "p");
            builder.returnNodes("p");
            builder.addWhereCondition("p", "age", SearchOperator.LESS_THAN_OR_EQUAL, 60);

            // When
            NeoCypherQuery cypherQuery = builder.build();

            // Then
            assertEquals("MATCH (p:Person) WHERE p.age <= $age_0 RETURN p", cypherQuery.getQuery());
            assertEquals(60, cypherQuery.getParameters().get("age_0"));
        }
    }

    public static class LogicalOperators {
        @Test
        public void multipleWhereCondition() {
            // Given
            NeoCypherQueryBuilder builder = new NeoCypherQueryBuilder(QueryType.MATCH);
            builder.nodeWithLabel("Person", "p");
            builder.returnNodes("p");

            builder.addWhereCondition("p", "title", SearchOperator.EQUALS, "Mr");
            builder.addWhereCondition("p", "lastName", SearchOperator.EQUALS, "Ellis");
            builder.addWhereCondition("p", "age", SearchOperator.GREATER_THAN_OR_EQUAL, 18);

            // When
            NeoCypherQuery cypherQuery = builder.build();

            // Then
            assertEquals("MATCH (p:Person) WHERE p.title = $title_0 AND p.lastName = $lastName_1 AND p.age >= $age_2 RETURN p", cypherQuery.getQuery());
            assertEquals("Mr", cypherQuery.getParameters().get("title_0"));
            assertEquals("Ellis", cypherQuery.getParameters().get("lastName_1"));
            assertEquals(18, cypherQuery.getParameters().get("age_2"));
        }

        @Test
        public void createOrWhereCondition() {
            // Given
            NeoCypherQueryBuilder builder = new NeoCypherQueryBuilder(QueryType.MATCH);
            builder.nodeWithLabel("Person", "p");
            builder.returnNodes("p");
            builder.setCurrentConditionLogicalOperator(SearchLogicalOperator.OR);
            builder.addWhereCondition("p", "lastName", SearchOperator.EQUALS, "Ellis");
            builder.addWhereCondition("p", "lastName", SearchOperator.EQUALS, "Smith");

            // When
            NeoCypherQuery cypherQuery = builder.build();

            // Then
            assertEquals("MATCH (p:Person) WHERE p.lastName = $lastName_0 OR p.lastName = $lastName_1 RETURN p", cypherQuery.getQuery());
            assertEquals("Ellis", cypherQuery.getParameters().get("lastName_0"));
            assertEquals("Smith", cypherQuery.getParameters().get("lastName_1"));
        }

        @Test
        public void createOrSubWhereCondition() {
            // Given
            NeoCypherQueryBuilder builder = new NeoCypherQueryBuilder(QueryType.MATCH);
            builder.nodeWithLabel("Person", "p");
            builder.returnNodes("p");
            builder.addWhereCondition("p", "lastName", SearchOperator.EQUALS, "Ellis");
            builder.orSubCondition();
            builder.addWhereCondition("p", "firstName", SearchOperator.EQUALS, "Dale");
            builder.addWhereCondition("p", "firstName", SearchOperator.EQUALS, "Faye");

            // When
            NeoCypherQuery cypherQuery = builder.build();

            // Then
            assertEquals("MATCH (p:Person) WHERE p.lastName = $lastName_0 AND (p.firstName = $firstName_1 OR p.firstName = $firstName_2) RETURN p", cypherQuery.getQuery());
            assertEquals("Ellis", cypherQuery.getParameters().get("lastName_0"));
            assertEquals("Dale", cypherQuery.getParameters().get("firstName_1"));
            assertEquals("Faye", cypherQuery.getParameters().get("firstName_2"));
        }

        @Test
        public void createAndSubWhereCondition() {
            // Given
            NeoCypherQueryBuilder builder = new NeoCypherQueryBuilder(QueryType.MATCH);
            builder.nodeWithLabel("Person", "p");
            builder.returnNodes("p");
            builder.setCurrentConditionLogicalOperator(SearchLogicalOperator.OR);

            builder.andSubCondition();
            builder.addWhereCondition("p", "firstName", SearchOperator.EQUALS, "Dale");
            builder.addWhereCondition("p", "lastName", SearchOperator.EQUALS, "Ellis");
            builder.endSubCondition();

            builder.andSubCondition();
            builder.addWhereCondition("p", "firstName", SearchOperator.EQUALS, "Faye");
            builder.addWhereCondition("p", "lastName", SearchOperator.EQUALS, "Lloyd");

            // When
            NeoCypherQuery cypherQuery = builder.build();

            // Then
            assertEquals("MATCH (p:Person) WHERE (p.firstName = $firstName_0 AND p.lastName = $lastName_1) OR (p.firstName = $firstName_2 AND p.lastName = $lastName_3) RETURN p", cypherQuery.getQuery());
            assertEquals("Dale", cypherQuery.getParameters().get("firstName_0"));
            assertEquals("Ellis", cypherQuery.getParameters().get("lastName_1"));
            assertEquals("Faye", cypherQuery.getParameters().get("firstName_2"));
            assertEquals("Lloyd", cypherQuery.getParameters().get("lastName_3"));
        }
    }

}