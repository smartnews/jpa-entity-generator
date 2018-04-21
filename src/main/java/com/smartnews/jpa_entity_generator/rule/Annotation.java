package com.smartnews.jpa_entity_generator.rule;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Represents Java annotations.
 */
@Data
public class Annotation implements Serializable {

    private String className;
    private String code;
    private List<AnnotationAttribute> attributes = new ArrayList<>();

    public String toString() {
        if (StringUtils.isEmpty(getCode())) {
            if (attributes != null && attributes.size() > 0) {
                String attributesPart = attributes.stream()
                        .map(a -> {
                            if (StringUtils.isEmpty(a.getCode())) {
                                return a.getName() + " = " + a.getValue();
                            } else {
                                return a.getCode();
                            }
                        })
                        .collect(Collectors.joining(", ", "(", ")"));
                return "@" + className + attributesPart;
            } else {
                return "@" + className;
            }
        } else {
            return getCode();
        }
    }

    public static Annotation fromCode(String code) {
        Annotation annotation = new Annotation();
        annotation.setCode(code);
        return annotation;
    }

    public static Annotation fromClassName(String className) {
        Annotation annotation = new Annotation();
        annotation.setClassName(className);
        return annotation;
    }
}
