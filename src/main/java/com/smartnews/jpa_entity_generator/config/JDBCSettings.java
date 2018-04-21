package com.smartnews.jpa_entity_generator.config;

import lombok.Data;

import java.io.Serializable;

/**
 * JDBC connection settings.
 */
@Data
public class JDBCSettings implements Serializable {

    private String url;
    private String username;
    private String password;
    private String driverClassName;
}