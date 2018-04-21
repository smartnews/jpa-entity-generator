package com.smartnews.jpa_entity_generator.rule;

import org.apache.commons.lang3.StringUtils;

import java.util.List;

/**
 * Provides #matches method to check if this rule matches a given class name.
 */
public interface ClassMatcher {

    /**
     * Returns a single partial-matching rule.
     */
    String getClassName();

    /**
     * Returns multiple partial-matching rules.
     */
    List<String> getClassNames();

    /**
     * Predicates if the rule this class holds matches a given class name.
     * @param className class name
     * @return true if the rule matches.
     */
    default boolean matches(String className) {
        if (StringUtils.isEmpty(getClassName()) && (getClassNames() == null || getClassNames().size() == 0)) {
            // global settings
            return true;
        }
        return (getClassName() != null && getClassName().equals(className)) ||
                (getClassNames() != null && getClassNames().contains(className));
    }

}