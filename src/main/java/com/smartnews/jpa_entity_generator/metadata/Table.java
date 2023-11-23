package com.smartnews.jpa_entity_generator.metadata;

import lombok.Data;

import java.util.*;

/**
 * Database metadata: a table
 */
@Data
public class Table {

    private String name;
    private Optional<String> schema = Optional.empty();
    private Optional<String> description = Optional.empty();
    private List<Column> columns = new ArrayList<>();
    private Map<String, ForeignKey> foreignKeyMap = new HashMap<>();
    private Map<String, List<ForeignKey>> foreignCompositeKeyMap = new HashMap<>();
    private List<String> importedKeys = new ArrayList<>();
}
