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
        if (matches(className) == false) {
            return false;
        }

        String singleTarget = getFieldName();
        if (singleTarget != null) {
            boolean matched = singleTarget.equals(fieldName) || fieldName.matches(singleTarget);
            if (matched) {
                return true;
            }
        }

        List<String> targets = getFieldNames();
        if (targets != null && targets.isEmpty() == false) {
            boolean matched = targets.contains(fieldName);
            if (matched) {
                return true;
            } else {
                for (String target : targets) {
                    if (fieldName.matches(target)) {
                        return true;
                    }
                }
            }
        }

        return false;
    }
}
