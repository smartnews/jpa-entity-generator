package com.smartnews.jpa_entity_generator.rule;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Rule used to filter the tables to generate entities.
 */
@Data
public class TableScanRule implements Serializable, TableMatcher {

    private String tableName;
    private List<String> tableNames = new ArrayList<>();
}
