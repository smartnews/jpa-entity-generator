package com.smartnews.jpa_entity_generator.rule;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Rule used to generate the type for a given field.
 */
@Data
public class FieldTypeRule implements Serializable, FieldMatcher {

    private String className;
    private List<String> classNames = new ArrayList<>();
    private String fieldName;
    private List<String> fieldNames = new ArrayList<>();
    private String typeName;
}
