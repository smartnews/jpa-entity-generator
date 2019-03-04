package com.smartnews.jpa_entity_generator;

import com.smartnews.jpa_entity_generator.config.CodeGeneratorConfig;
import com.smartnews.jpa_entity_generator.metadata.Column;
import com.smartnews.jpa_entity_generator.metadata.Table;
import com.smartnews.jpa_entity_generator.metadata.TableMetadataFetcher;
import com.smartnews.jpa_entity_generator.rule.*;
import com.smartnews.jpa_entity_generator.util.NameConverter;
import com.smartnews.jpa_entity_generator.util.TypeConverter;
import freemarker.template.TemplateException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.SerializationUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

/**
 * Lombok-wired JPA entity code generator.
 */
@Slf4j
public class CodeGenerator {

    private static final List<String> EXPECTED_ID_ANNOTATION_CLASS_NAMES = Arrays.asList("Id", "javax.persistence.Id");

    private static final Predicate<CodeRenderer.RenderingData.Field> hasIdAnnotation = (f) -> {
        boolean isPrimaryKey = f.isPrimaryKey();
        boolean hasIdAnnotation = f.getAnnotations().stream()
                .anyMatch(a -> EXPECTED_ID_ANNOTATION_CLASS_NAMES.contains(a.getClassName()));
        return isPrimaryKey || hasIdAnnotation;
    };

    public static void generateAll(CodeGeneratorConfig originalConfig) throws SQLException, IOException, TemplateException {
        generateAll(originalConfig, false);
    }

    /**
     * Generates all entities from existing tables.
     */
    public static void generateAll(CodeGeneratorConfig originalConfig, boolean isJpa1) throws SQLException, IOException, TemplateException {
        Path dir = Paths.get(originalConfig.getOutputDirectory() + "/" +
                (isJpa1 ? originalConfig.getPackageNameForJpa1().replaceAll("\\.", "/") : originalConfig.getPackageName().replaceAll("\\.", "/")));
        Files.createDirectories(dir);

        TableMetadataFetcher metadataFetcher = new TableMetadataFetcher();
        List<String> allTableNames = metadataFetcher.getTableNames(originalConfig.getJdbcSettings());
        List<String> tableNames = filterTableNames(originalConfig, allTableNames);
        for (String tableName : tableNames) {
            boolean shouldExclude = originalConfig.getTableExclusionRules().stream().anyMatch(rule -> rule.matches(tableName));
            if (shouldExclude) {
                log.debug("Skipped to generate entity for {}", tableName);
                continue;
            }
            CodeGeneratorConfig config = SerializationUtils.clone(originalConfig);
            Table table = metadataFetcher.getTable(config.getJdbcSettings(), tableName);

            CodeRenderer.RenderingData data = new CodeRenderer.RenderingData();
            data.setJpa1Compatible(isJpa1);
            data.setEnableJSR305(config.isEnableJSR305NullCheck());

            if (isJpa1) {
                data.setPackageName(config.getPackageNameForJpa1());
            } else {
                data.setPackageName(config.getPackageName());
            }

            String className = NameConverter.toClassName(table.getName(), config.getClassNameRules());
            data.setClassName(className);
            data.setTableName(table.getName());

            ClassAnnotationRule entityClassAnnotationRule = new ClassAnnotationRule();
            Annotation entityAnnotation = Annotation.fromClassName("javax.persistence.Entity");
            AnnotationAttribute entityAnnotationValueAttr = new AnnotationAttribute();
            entityAnnotationValueAttr.setName("name");
            entityAnnotationValueAttr.setValue("\"" + data.getPackageName() + "." + data.getClassName() + "\"");
            entityAnnotation.getAttributes().add(entityAnnotationValueAttr);
            entityClassAnnotationRule.setAnnotations(Arrays.asList(entityAnnotation));
            entityClassAnnotationRule.setClassName(className);
            config.getClassAnnotationRules().add(entityClassAnnotationRule);

            data.setClassComment(buildClassComment(className, table, config.getClassAdditionalCommentRules()));

            data.setImportRules(config.getImportRules().stream()
                    .filter(r -> r.matches(className))
                    .collect(toList()));

            List<CodeRenderer.RenderingData.Field> fields = table.getColumns().stream().map(c -> {
                CodeRenderer.RenderingData.Field f = new CodeRenderer.RenderingData.Field();

                String fieldName = NameConverter.toFieldName(c.getName());
                f.setName(fieldName);
                f.setColumnName(c.getName());
                f.setNullable(c.isNullable());

                f.setComment(buildFieldComment(className, f.getName(), c, config.getFieldAdditionalCommentRules()));

                f.setAnnotations(config.getFieldAnnotationRules().stream()
                        .filter(rule -> rule.matches(className, f.getName()))
                        .flatMap(rule -> rule.getAnnotations().stream())
                        .peek(a -> a.setClassName(collectAndConvertFQDN(a.getClassName(), data.getImportRules())))
                        .collect(toList()));

                Optional<FieldTypeRule> fieldTypeRule =
                        orEmptyListIfNull(config.getFieldTypeRules()).stream()
                                .filter(b -> b.matches(className, fieldName)).findFirst();
                if (fieldTypeRule.isPresent()) {
                    f.setType(fieldTypeRule.get().getTypeName());
                } else {
                    f.setType(TypeConverter.toJavaType(c.getTypeCode()));
                }

                Optional<FieldDefaultValueRule> fieldDefaultValueRule =
                        orEmptyListIfNull(config.getFieldDefaultValueRules()).stream()
                                .filter(r -> r.matches(className, fieldName)).findFirst();
                if (fieldDefaultValueRule.isPresent()) {
                    f.setDefaultValue(fieldDefaultValueRule.get().getDefaultValue());
                }
                if (StringUtils.isNotEmpty(config.getGeneratedValueStrategy())) {
                    f.setGeneratedValueStrategy(config.getGeneratedValueStrategy());
                }

                f.setAutoIncrement(c.isAutoIncrement());
                f.setPrimaryKey(c.isPrimaryKey());
                return f;

            }).collect(toList());

            if (fields.stream().noneMatch(hasIdAnnotation)) {
                throw new IllegalStateException("Entity class " + data.getClassName() + " has no @Id field!");
            }

            data.setFields(fields);
            data.setPrimaryKeyFields(fields.stream().filter(CodeRenderer.RenderingData.Field::isPrimaryKey).collect(toList()));

            data.setInterfaceNames(orEmptyListIfNull(config.getInterfaceRules()).stream()
                    .filter(r -> r.matches(className))
                    .peek(rule -> {
                        for (Interface i : rule.getInterfaces()) {
                            i.setName(collectAndConvertFQDN(i.getName(), data.getImportRules()));
                            i.setGenericsClassNames(i.getGenericsClassNames().stream()
                                    .map(cn -> collectAndConvertFQDN(cn, data.getImportRules()))
                                    .collect(toList()));
                        }
                    })
                    .flatMap(r -> r.getInterfaces().stream().map(i -> {
                        String genericsPart = i.getGenericsClassNames().size() > 0 ?
                                i.getGenericsClassNames().stream()
                                        .map(n -> n.equals("{className}") ? className : n)
                                        .collect(Collectors.joining(", ", "<", ">"))
                                : "";
                        return i.getName() + genericsPart;
                    }))
                    .collect(toList()));

            data.setClassAnnotationRules(orEmptyListIfNull(config.getClassAnnotationRules()).stream()
                    .filter(r -> r.matches(className))
                    .peek(rule -> rule.getAnnotations().forEach(a -> {
                        a.setClassName(collectAndConvertFQDN(a.getClassName(), data.getImportRules()));
                    }))
                    .collect(toList()));

            orEmptyListIfNull(config.getAdditionalCodeRules()).forEach(rule -> {
                if (rule.matches(className)) {
                    String code = null;
                    if (isJpa1 && rule.getJpa1Code() != null) {
                        code = rule.getJpa1Code();
                    } else if (rule.getCode() != null) {
                        code = rule.getCode();
                    }

                    if (code != null) {
                        StringJoiner joiner = new StringJoiner("\n  ", "  ", "");
                        for (String line : code.split("\\n")) {
                            joiner.add(line);
                        }
                        String optimizedCode = joiner.toString();
                        if (rule.getPosition() == AdditionalCodePosition.Top) {
                            data.getTopAdditionalCodeList().add(optimizedCode);
                        } else {
                            data.getBottomAdditionalCodeList().add(optimizedCode);
                        }
                    }
                }
            });

            orEmptyListIfNull(data.getImportRules()).sort(Comparator.comparing(ImportRule::getImportValue));

            String code = CodeRenderer.render("entityGen/entity.ftl", data);

            String filepath = config.getOutputDirectory() + "/" + data.getPackageName().replaceAll("\\.", "/") + "/" + className + ".java";
            Path path = Paths.get(filepath);
            if (!Files.exists(path)) {
                Files.createFile(path);
            }
            Files.write(path, code.getBytes());

            log.debug("path: {}, code: {}", path, code);
        }
    }

    private static List<String> filterTableNames(CodeGeneratorConfig config, List<String> allTableNames) {
        String tableScanMode = config.getTableScanMode();
        if (tableScanMode == null) {
            return allTableNames;
        }
        String normalizedTableScanMode = tableScanMode.trim().toLowerCase(Locale.ENGLISH);
        if (normalizedTableScanMode.equals("all")) {
            return allTableNames;
        } else if (normalizedTableScanMode.equals("rulebased")) {
            List<String> filteredTableNames = new ArrayList<>();
            for (String tableName : allTableNames) {
                boolean isScanTarget = true;
                for (TableScanRule rule : config.getTableScanRules()) {
                    if (!rule.matches(tableName)) {
                        isScanTarget = false;
                        break;
                    }
                }
                if (isScanTarget) {
                    filteredTableNames.add(tableName);
                }
            }
            return filteredTableNames;
        } else {
            throw new IllegalStateException("Invalid value (" + tableScanMode + ") is specified for tableScanName");
        }
    }

    private static String buildClassComment(String className, Table table, List<ClassAdditionalCommentRule> rules) {
        List<String> comment = table.getDescription()
                .map(c -> Arrays.stream(c.split("\n")).filter(l -> l != null && !l.isEmpty()).collect(toList()))
                .orElse(Collections.emptyList());
        List<String> additionalComments = rules.stream()
                .filter(r -> r.matches(className))
                .map(ClassAdditionalCommentRule::getComment)
                .flatMap(c -> Arrays.stream(c.split("\n")))
                .collect(toList());
        comment.addAll(additionalComments);
        if (comment.size() > 0) {
            return comment.stream().collect(joining("\n * ", "/**\n * ", "\n */"));
        } else {
            return null;
        }
    }

    private static String buildFieldComment(String className, String fieldName, Column column, List<FieldAdditionalCommentRule> rules) {
        List<String> comment = column.getDescription()
                .map(c -> Arrays.stream(c.split("\n")).filter(l -> l != null && !l.isEmpty()).collect(toList()))
                .orElse(Collections.emptyList());
        List<String> additionalComments = rules.stream()
                .filter(r -> r.matches(className, fieldName))
                .map(FieldAdditionalCommentRule::getComment)
                .flatMap(c -> Arrays.stream(c.split("\n")))
                .collect(toList());
        comment.addAll(additionalComments);
        if (comment.size() > 0) {
            return comment.stream().collect(joining("\n   * ", "  /**\n   * ", "\n   */"));
        } else {
            return null;
        }
    }

    private static <T> List<T> orEmptyListIfNull(List<T> list) {
        return Optional.ofNullable(list).orElse(Collections.emptyList());
    }

    private static String collectAndConvertFQDN(String fqdn, List<ImportRule> imports) {
        if (fqdn != null && fqdn.contains(".") && fqdn.matches("^[a-zA-Z0-9.]+$")) {
            if (imports.stream().noneMatch(i -> i.importValueContains(fqdn))) {
                ImportRule rule = new ImportRule();
                rule.setImportValue(fqdn);
                imports.add(rule);
            }
            String[] elements = fqdn.split("\\.");
            return elements[elements.length - 1];
        } else {
            return fqdn;
        }
    }

}
