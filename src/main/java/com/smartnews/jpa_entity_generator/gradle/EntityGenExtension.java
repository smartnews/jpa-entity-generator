package com.smartnews.jpa_entity_generator.gradle;

import lombok.Data;

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
}
