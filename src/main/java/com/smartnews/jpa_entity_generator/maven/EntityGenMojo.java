package com.smartnews.jpa_entity_generator.maven;

import com.smartnews.jpa_entity_generator.CodeGenerator;
import com.smartnews.jpa_entity_generator.config.CodeGeneratorConfig;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;

import java.util.HashMap;
import java.util.Map;

/**
 * @goal generateAll
 * @phase process-sources
 */
public class EntityGenMojo extends AbstractMojo {

    protected Map<String, String> environment = new HashMap<>();

    /**
     * @parameter configPath
     */
    protected String configPath = "src/main/resources/entityGenConfig.yml";

    @Override
    public void execute() throws MojoExecutionException {
        try {
            CodeGeneratorConfig config = CodeGeneratorConfig.load(configPath, environment);
            CodeGenerator.generateAll(config);
        } catch (Exception e) {
            throw new MojoExecutionException(e.getMessage(), e);
        }
    }
}
