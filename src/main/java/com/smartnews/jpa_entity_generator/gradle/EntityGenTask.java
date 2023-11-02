package com.smartnews.jpa_entity_generator.gradle;

import com.smartnews.jpa_entity_generator.CodeGenerator;
import com.smartnews.jpa_entity_generator.config.CodeGeneratorConfig;
import freemarker.template.TemplateException;
import org.gradle.api.DefaultTask;
import org.gradle.api.tasks.TaskAction;

import java.io.IOException;
import java.sql.SQLException;

/**
 * entityGen Gradle task.
 */
public class EntityGenTask extends DefaultTask {

    @TaskAction
    public void generateAll() throws IOException, SQLException, TemplateException {
        EntityGenExtension ext = getProject().getExtensions().getByType(EntityGenExtension.class);
        if (ext == null) {
            ext = new EntityGenExtension();
        }
        CodeGeneratorConfig config = CodeGeneratorConfig.load(ext.getConfigPath(), ext.getEnvironment());
        if (config.isJpa1SupportRequired()) {
            if (config.getPackageName().equals(config.getPackageNameForJpa1())) {
                throw new IllegalStateException("packageName and packageNameForJpa1 must be different.");
            }
            CodeGenerator.generateAll(config, true);
        }
        CodeGenerator.generateAll(config, false);
    }
}
