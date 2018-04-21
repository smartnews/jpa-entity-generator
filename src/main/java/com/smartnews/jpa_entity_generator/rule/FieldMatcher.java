package com.smartnews.jpa_entity_generator.rule;

import java.util.List;

/**
 * Provides #matches method to check if this rule matches a given field name.
 */
public interface FieldMatcher extends ClassMatcher {

    /**
     * Returns a single partial-matching rule.
     */
    String getFieldName();

    /**
     * Returns multiple partial-matching rules.
     */
    List<String> getFieldNames();

    /**
     * Predicates if the rule this class holds matches a given combination of class name and field name.
     * @param className class name
     * @param fieldName field name
     * @return true if the rule matches.
     */
    default boolean matches(String className, String fieldName) {
        return matches(className) &&
                ((getFieldName() != null && getFieldName().equals(fieldName)) ||
                        (getFieldNames() != null && getFieldNames().contains(fieldName)));
    }
}
