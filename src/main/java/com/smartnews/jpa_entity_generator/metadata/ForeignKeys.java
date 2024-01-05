package com.smartnews.jpa_entity_generator.metadata;

import lombok.Data;

@Data
public class ForeignKeys {
    private String name;
    private String columnName;
    private String pkColumnName;
    private String pkTable;
}
