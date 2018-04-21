package com.smartnews.jpa_entity_generator.rule;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents an interface.
 */
@Data
public class Interface implements Serializable {

    private String name;
    private List<String> genericsClassNames = new ArrayList<>();
}
