/**
 * NOTE:
 * Avoid using final for fields (e.g. private final List&lt;AnnotationAttribute&gt; attributes = new ArrayList&lt;&gt;();)
 * SnakeYAML fails to bind values to the bean if fields are marked as final.
 */
package com.smartnews.jpa_entity_generator.rule;