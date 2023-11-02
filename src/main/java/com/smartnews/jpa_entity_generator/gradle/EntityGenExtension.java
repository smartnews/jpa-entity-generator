package com.smartnews.jpa_entity_generator.gradle;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * <pre>
 * entityGen {
 *   configPath = "src/main/resources/entityGenConfig.yml"
 * }
 * </pre>
 */
@Data
public class EntityGenExtension {
    private String configPath = "entityGenConfig.yml";
    private Map<String, String> environment = new HashMap<>();
}
