package com.smartnews.jpa_entity_generator.rule;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Rule used to generate annotations for a class.
 */
@Data
public class ClassAnnotationRule implements Serializable, ClassMatcher {

    private String className;
    private List<String> classNames = new ArrayList<>();
    private List<Annotation> annotations = new ArrayList<>();

    public static ClassAnnotationRule createGlobal(Annotation... annotations) {
        ClassAnnotationRule rule = new ClassAnnotationRule();
        rule.setAnnotations(Arrays.asList(annotations));
        return rule;
    }
}
