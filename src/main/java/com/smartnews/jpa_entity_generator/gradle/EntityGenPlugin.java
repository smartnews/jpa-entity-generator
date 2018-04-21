package com.smartnews.jpa_entity_generator.gradle;

import org.gradle.api.Plugin;
import org.gradle.api.Project;

/**
 * entityGen Gradle plugin.
 */
public class EntityGenPlugin implements Plugin<Project> {

    @Override
    public void apply(Project project) {
        project.getExtensions().create("entityGen", EntityGenExtension.class);
        project.getTasks().create("entityGen", EntityGenTask.class);
    }
}
