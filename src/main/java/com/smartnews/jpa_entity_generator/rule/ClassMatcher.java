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

        String singleTarget = getClassName();
        if (singleTarget != null) {
            boolean matched = singleTarget.equals(className) || className.matches(singleTarget);
            if (matched) {
                return true;
            }
        }

        List<String> targets = getClassNames();
        if (targets != null && targets.isEmpty() == false) {
            boolean matched = targets.contains(className);
            if (matched) {
                return true;
            } else {
                for (String target : targets) {
                    if (className.matches(target)) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

}