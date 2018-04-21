package com.smartnews.jpa_entity_generator.rule;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Rule used to generate additional code in entity classes.
 */
@Data
public class AdditionalCodeRule implements Serializable, ClassMatcher {

    private String className;
    private List<String> classNames = new ArrayList<>();
    private AdditionalCodePosition position = AdditionalCodePosition.Bottom;
    private String code;
    private String jpa1Code;
}
