package com.smartnews.jpa_entity_generator.metadata;

import lombok.Data;

@Data
public class ForeignKey {
    private String columnName;
    private String pkColumnName;
    private String pkTable;
}
