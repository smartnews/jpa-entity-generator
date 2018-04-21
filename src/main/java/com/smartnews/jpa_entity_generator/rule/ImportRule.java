package com.smartnews.jpa_entity_generator.rule;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Rule used to generate imports.
 */
@Data
public class ImportRule implements Serializable, ClassMatcher {

    private String className;
    private List<String> classNames = new ArrayList<>();
    private String importValue;

    public boolean importValueContains(String className) {
        if (importValue.startsWith("static")) {
            return false;
        }
        if (importValue.endsWith(".*")) {
            return className.replaceFirst("\\.[^\\.]+$", ".*").equals(importValue);
        } else {
            return className.equals(importValue);
        }
    }

    public static ImportRule createGlobal(String importValue) {
        ImportRule rule = new ImportRule();
        rule.setImportValue(importValue);
        return rule;
    }
}