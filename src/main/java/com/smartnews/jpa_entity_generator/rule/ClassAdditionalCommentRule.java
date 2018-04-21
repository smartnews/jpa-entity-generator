package com.smartnews.jpa_entity_generator.rule;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Rule used to generate additional class comments.
 */
@Data
public class ClassAdditionalCommentRule implements Serializable, ClassMatcher {

    private String className;
    private List<String> classNames = new ArrayList<>();
    private String comment;
}
