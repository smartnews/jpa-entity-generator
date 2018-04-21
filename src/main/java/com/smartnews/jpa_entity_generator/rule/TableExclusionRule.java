package com.smartnews.jpa_entity_generator.rule;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Rule used to exclude a portion of tables from the code generation targets.
 */
@Data
public class TableExclusionRule implements Serializable, TableMatcher {

    private String tableName;
    private List<String> tableNames = new ArrayList<>();
}
