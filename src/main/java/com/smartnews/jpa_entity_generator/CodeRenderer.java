package com.smartnews.jpa_entity_generator;

import com.smartnews.jpa_entity_generator.rule.Annotation;
import com.smartnews.jpa_entity_generator.rule.ClassAnnotationRule;
import com.smartnews.jpa_entity_generator.rule.ImportRule;
import com.smartnews.jpa_entity_generator.util.ResourceReader;
import freemarker.cache.StringTemplateLoader;
import freemarker.ext.beans.BeansWrapper;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;
import lombok.Data;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Code renderer.
 */
public class CodeRenderer {

    /**
     * Renders source code by using Freemarker template engine.
     */
    public static String render(String templatePath, RenderingData data) throws IOException, TemplateException {
        Configuration config = new Configuration(Configuration.DEFAULT_INCOMPATIBLE_IMPROVEMENTS);
        StringTemplateLoader templateLoader = new StringTemplateLoader();
        String source;
        try (InputStream is = ResourceReader.getResourceAsStream(templatePath);
             BufferedReader buffer = new BufferedReader(new InputStreamReader(is))) {
            source = buffer.lines().collect(Collectors.joining("\n"));
        }
        templateLoader.putTemplate("template", source);
        config.setTemplateLoader(templateLoader);
        config.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
        config.setObjectWrapper(new BeansWrapper(Configuration.DEFAULT_INCOMPATIBLE_IMPROVEMENTS));
        config.setWhitespaceStripping(true);

        try (Writer writer = new java.io.StringWriter()) {
            Template template = config.getTemplate("template");
            template.process(data, writer);
            return writer.toString();
        }
    }

    /**
     * Data used when rendering source code.
     */
    @Data
    public static class RenderingData {

        private String packageName;
        private String tableName;
        private String className;
        private String classComment;

        private boolean jpa1Compatible = false;

        private List<String> topAdditionalCodeList = new ArrayList<>();
        private List<String> bottomAdditionalCodeList = new ArrayList<>();

        private List<ImportRule> importRules = new ArrayList<>();
        private List<ClassAnnotationRule> classAnnotationRules = new ArrayList<>();
        private List<String> interfaceNames = new ArrayList<>();
        private List<Field> fields = new ArrayList<>();
        private List<Field> primaryKeyFields = new ArrayList<>();

        @Data
        public static class Field {
            private String name;
            private String columnName;
            private String type;
            private String comment;
            private String defaultValue;
            private boolean primaryKey;
            private boolean autoIncrement;
            private String generatedValueStrategy;
            private List<Annotation> annotations = new ArrayList<>();
        }
    }
}
