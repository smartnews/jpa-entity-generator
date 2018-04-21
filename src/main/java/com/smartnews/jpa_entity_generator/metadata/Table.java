package com.smartnews.jpa_entity_generator.metadata;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Database metadata: a table
 */
@Data
public class Table {

    private String name;
    private Optional<String> schema = Optional.empty();
    private Optional<String> description = Optional.empty();
    private List<Column> columns = new ArrayList<>();
}
