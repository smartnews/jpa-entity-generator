package com.smartnews.jpa_entity_generator.config;

import com.smartnews.jpa_entity_generator.rule.*;
import com.smartnews.jpa_entity_generator.util.ResourceReader;
import lombok.Data;
import org.yaml.snakeyaml.Yaml;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Code generator's configuration.
 */
@Data
public class CodeGeneratorConfig implements Serializable {

    // ----------
    // NOTE: Explicitly having NoArgsConstructor/AllArgsConstructor is necessary as as a workaround to enable using @Builder
    // see also: https://github.com/rzwitserloot/lombok/issues/816
    public static final List<ClassAnnotationRule> CLASS_ANNOTATIONS_NECESSARY_FOR_LOMBOK_BUILDER = Arrays.asList(
            ClassAnnotationRule.createGlobal(Annotation.fromClassName("lombok.NoArgsConstructor")),
            ClassAnnotationRule.createGlobal(Annotation.fromClassName("lombok.AllArgsConstructor"))
    );

    public static final List<ImportRule> IMPORTS_NECESSARY_FOR_LOMBOK_BUILDER = Arrays.asList(
            ImportRule.createGlobal("lombok.NoArgsConstructor"),
            ImportRule.createGlobal("lombok.AllArgsConstructor")
    );

    // ----------
    // Preset

    // NOTE: @Table(name = "${tableName}") needs tableName of target table.
    private static List<ClassAnnotationRule> PRESET_CLASS_ANNOTATIONS = Arrays.asList(
            ClassAnnotationRule.createGlobal(Annotation.fromClassName("lombok.Data"))
    );

    private static final List<ImportRule> PRESET_IMPORTS = Arrays.asList(
            ImportRule.createGlobal("java.sql.*"),
            ImportRule.createGlobal("javax.persistence.*"),
            ImportRule.createGlobal("lombok.Data")
    );
    // ----------

    public CodeGeneratorConfig() {
    }

    public void setUpPresetRules() {
        getClassAnnotationRules().addAll(0, PRESET_CLASS_ANNOTATIONS);
        getImportRules().addAll(0, PRESET_IMPORTS);
        if (autoPreparationForLombokBuilderEnabled) {
            getClassAnnotationRules().addAll(CLASS_ANNOTATIONS_NECESSARY_FOR_LOMBOK_BUILDER);
            getImportRules().addAll(IMPORTS_NECESSARY_FOR_LOMBOK_BUILDER);
        }
    }

    private JDBCSettings jdbcSettings;

    private List<String> tableNames = new ArrayList<>();
    private List<TableExclusionRule> tableExclusionRules = new ArrayList<>();

    private String outputDirectory = "src/main/java";
    private String packageName = "com.smartnews.db";
    private String packageNameForJpa1 = "com.smartnews.db.jpa1";
    private boolean jpa1SupportRequired;

    // NOTE: Explicitly having NoArgsConstructor/AllArgsConstructor is necessary as as a workaround to enable using @Builder
    // see also: https://github.com/rzwitserloot/lombok/issues/816
    private boolean autoPreparationForLombokBuilderEnabled;

    private List<ImportRule> importRules = new ArrayList<>();

    private List<ClassNameRule> classNameRules = new ArrayList<>();
    private List<ClassAnnotationRule> classAnnotationRules = new ArrayList<>();
    private List<InterfaceRule> interfaceRules = new ArrayList<>();
    private List<ClassAdditionalCommentRule> classAdditionalCommentRules = new ArrayList<>();

    private List<FieldTypeRule> fieldTypeRules = new ArrayList<>();
    private List<FieldAnnotationRule> fieldAnnotationRules = new ArrayList<>();
    private List<FieldDefaultValueRule> fieldDefaultValueRules = new ArrayList<>();
    private List<FieldAdditionalCommentRule> fieldAdditionalCommentRules = new ArrayList<>();

    private List<AdditionalCodeRule> additionalCodeRules = new ArrayList<>();

    private static final Yaml YAML = new Yaml();

    public static CodeGeneratorConfig load(String path) throws IOException {
        try (InputStream is = ResourceReader.getResourceAsStream(path)) {
            try (Reader reader = new InputStreamReader(is)) {
                CodeGeneratorConfig config = YAML.loadAs(reader, CodeGeneratorConfig.class);
                config.setUpPresetRules();
                return config;
            }
        }
    }

}
