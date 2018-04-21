package com.smartnews.jpa_entity_generator.rule;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Rule used to generate an interface.
 */
@Data
public class InterfaceRule implements Serializable, ClassMatcher {

    /**
     * A single partial-matching rule.
     */
    private String className;

    /**
     * multiple partial-matching rule.
     */
    private List<String> classNames = new ArrayList<>();

    /**
     * The interfaces to be implemented.
     */
    private List<Interface> interfaces = new ArrayList<>();

}
