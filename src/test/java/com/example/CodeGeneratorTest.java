package com.example;

import com.smartnews.jpa_entity_generator.CodeGenerator;
import com.smartnews.jpa_entity_generator.config.CodeGeneratorConfig;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import com.example.unit.DatabaseUtil;

import java.util.HashMap;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CodeGeneratorTest {

    @BeforeClass
    public static void setupDatabase() throws Exception {
        DatabaseUtil.init();
    }

    @Test
    public void _01_generateAll_TableScanMode_Is_Default() throws Exception {
        CodeGeneratorConfig config = CodeGeneratorConfig.load("entityGenConfig.yml", new HashMap<>());
        config.setJpa1SupportRequired(true);
        config.setOutputDirectory("src/test/java");
        CodeGenerator.generateAll(config, true);
        CodeGenerator.generateAll(config, false);
    }

    @Test
    public void _02_generateAll_TableScanMode_Is_RuleBased() throws Exception {
        CodeGeneratorConfig config = CodeGeneratorConfig.load("entityGenConfig2.yml", new HashMap<>());
        config.setJpa1SupportRequired(true);
        config.setOutputDirectory("src/test/java");
        CodeGenerator.generateAll(config, true);
        CodeGenerator.generateAll(config, false);
    }

    @Test
    public void _03_generateAll_TableScanMode_Is_Default_use_Jakarta_no_jsr305() throws Exception {
        CodeGeneratorConfig config = CodeGeneratorConfig.load("entityGenConfig3.yml", new HashMap<>());
        config.setJpa1SupportRequired(true);
        config.setOutputDirectory("src/test/java");
        CodeGenerator.generateAll(config, true);
        CodeGenerator.generateAll(config, false);
    }

    @Test
    public void _04_generateAll_TableScanMode_Is_RuleBaseduse_Jakarta_and_jsr305() throws Exception {
        CodeGeneratorConfig config = CodeGeneratorConfig.load("entityGenConfig4.yml", new HashMap<>());
        config.setJpa1SupportRequired(true);
        config.setOutputDirectory("src/test/java");
        CodeGenerator.generateAll(config, true);
        CodeGenerator.generateAll(config, false);
    }

}
