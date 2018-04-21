package com.smartnews.jpa_entity_generator.util;

import com.smartnews.jpa_entity_generator.rule.ClassNameRule;
import com.google.common.base.CaseFormat;

import java.util.List;

/**
 * Utility about name conversions between table/column names and class/field names.
 */
public class NameConverter {

    private NameConverter() {
    }

    public static String toClassName(String tableName, List<ClassNameRule> rules) {
        for (ClassNameRule rule : rules) {
            if (rule.getTableName().equals(tableName)) {
                return rule.getClassName();
            }
        }
        return CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, tableName);
    }

    public static String toFieldName(String tableName) {
        return CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, tableName);
    }
}
