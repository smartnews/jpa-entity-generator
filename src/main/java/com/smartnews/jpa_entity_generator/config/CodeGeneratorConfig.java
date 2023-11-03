package com.smartnews.jpa_entity_generator.config;

import com.smartnews.jpa_entity_generator.rule.*;
import com.smartnews.jpa_entity_generator.util.ResourceReader;
import lombok.Data;
import org.yaml.snakeyaml.Yaml;

import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
    private static final List<ClassAnnotationRule> PRESET_CLASS_ANNOTATIONS = Arrays.asList(
            ClassAnnotationRule.createGlobal(Annotation.fromClassName("lombok.Data"))
    );

    private static final List<ImportRule> PRESET_IMPORTS = Arrays.asList(
            ImportRule.createGlobal("java.sql.*"),
            // Can be removed after no javax support and replace to jakarta.persistence.*
            ImportRule.createGlobal("javax.persistence.*"),
            ImportRule.createGlobal("lombok.Data")
    );

    private static final List<ImportRule> PRESET_JAKARTA_IMPORTS = Arrays.asList(
            ImportRule.createGlobal("java.sql.*"),
            ImportRule.createGlobal("jakarta.persistence.*"),
            ImportRule.createGlobal("lombok.Data")
    );

    // Can be removed after no javax support
    private static final List<ImportRule> JSR_305_PRESET_IMPORTS = Arrays.asList(
            ImportRule.createGlobal("javax.annotation.Nonnull"),
            ImportRule.createGlobal("javax.annotation.Nullable")
    );

    private static final List<ImportRule> JAKARTA_ANNOTATION_PRESET_IMPORTS = Arrays.asList(
            ImportRule.createGlobal("jakarta.annotation.Nonnull"),
            ImportRule.createGlobal("jakarta.annotation.Nullable")
    );
    // ----------

    public CodeGeneratorConfig() {
    }

    public void loadEnvVariables(Map<String, String> environment) {
        // JDBC settings
        JDBCSettings settings = getJdbcSettings();
        if (hasEnvVariables(settings.getUrl())) {
            settings.setUrl(replaceEnvVariables(settings.getUrl(), environment));
        }
        if (hasEnvVariables(settings.getUsername())) {
            settings.setUsername(replaceEnvVariables(settings.getUsername(), environment));
        }
        if (hasEnvVariables(settings.getPassword())) {
            settings.setPassword(replaceEnvVariables(settings.getPassword(), environment));
        }
        if (hasEnvVariables(settings.getDriverClassName())) {
            settings.setDriverClassName(replaceEnvVariables(settings.getDriverClassName(), environment));
        }
    }

    static boolean hasEnvVariables(String value) {
        return value != null && value.contains("${");
    }

    private static final Pattern REPLACE_ENV_VARIABLES_PATTERN = Pattern.compile("(\\$\\{[^}]+\\})");

    static String replaceEnvVariables(String value, Map<String, String> environment) {
        Map<String, String> envMap = new HashMap<>(environment);
        envMap.putAll(System.getenv());
        String text = value;

        for (Map.Entry<String, String> entry : envMap.entrySet()) {
            String k = entry.getKey();
            String v = entry.getValue();
            text = text.replaceAll("\\$\\{" + k + "}", v);
        }
        return text;
    }

    public void setUpPresetRules() {
        getClassAnnotationRules().addAll(0, PRESET_CLASS_ANNOTATIONS);
        if (useJakarta) {
            getImportRules().addAll(0, PRESET_JAKARTA_IMPORTS);
        } else {
            getImportRules().addAll(0, PRESET_IMPORTS);
        }
        if (autoPreparationForLombokBuilderEnabled) {
            getClassAnnotationRules().addAll(CLASS_ANNOTATIONS_NECESSARY_FOR_LOMBOK_BUILDER);
            getImportRules().addAll(IMPORTS_NECESSARY_FOR_LOMBOK_BUILDER);
        }
        if (jsr305AnnotationsRequired) {
            if (useJakarta) {
                getImportRules().addAll(JAKARTA_ANNOTATION_PRESET_IMPORTS);
            } else {
                getImportRules().addAll(JSR_305_PRESET_IMPORTS);
            }
        }
    }

    private JDBCSettings jdbcSettings;

    private List<String> tableNames = new ArrayList<>();
    private String tableScanMode = "All"; // possible values: All, RuleBased

    private List<TableScanRule> tableScanRules = new ArrayList<>();
    private List<TableExclusionRule> tableExclusionRules = new ArrayList<>();

    // @GeneratedValue(strategy = GenerationType.IDENTITY)
    // Possible values: TABLE, SEQUENCE, IDENTITY, AUTO
    // If you don't need to specify the `strategy`, set null value.
    private String generatedValueStrategy = "IDENTITY";

    private String outputDirectory = "src/main/java";
    private String packageName = "com.smartnews.db";
    private String packageNameForJpa1 = "com.smartnews.db.jpa1";
    private boolean jpa1SupportRequired;
    private boolean jsr305AnnotationsRequired;
    // Can be removed after no javax support and replace jsr305AnnotationsRequired to jakartaAnnotationRequired
    private boolean useJakarta;
    private boolean usePrimitiveForNonNullField;

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

    public static CodeGeneratorConfig load(String path, Map<String, String> environment) throws IOException {
        try (InputStream is = ResourceReader.getResourceAsStream(path)) {
            try (Reader reader = new InputStreamReader(is)) {
                CodeGeneratorConfig config = YAML.loadAs(reader, CodeGeneratorConfig.class);
                config.loadEnvVariables(environment);
                config.setUpPresetRules();
                return config;
            }
        }
    }

}
