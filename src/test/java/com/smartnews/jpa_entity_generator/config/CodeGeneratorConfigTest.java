package com.smartnews.jpa_entity_generator.config;

import org.junit.Test;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.smartnews.jpa_entity_generator.config.CodeGeneratorConfig.*;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class CodeGeneratorConfigTest {

    @Test
    public void testHasEnvVariables() {
        {
            assertFalse(hasEnvVariables(""));
            assertFalse(hasEnvVariables(" "));
            assertFalse(hasEnvVariables("foo"));
            assertFalse(hasEnvVariables("foo barr"));
            assertFalse(hasEnvVariables("foo $ test"));
        }
        {
            assertTrue(hasEnvVariables("${TEST}"));
            assertTrue(hasEnvVariables("Something is ${WRONG}"));
            assertTrue(hasEnvVariables("${WHAT} is happening?"));
        }
    }

    @Test
    public void testReplaceEnvVariables() {
        Map<String, String> env = System.getenv();
        List<String> keys = new ArrayList<>(env.keySet());
        String k1 = keys.get(0);
        String v1 = env.get(k1);
        String k2 = keys.get(1);
        String v2 = env.get(k2);
        String k3 = keys.get(2);
        String v3 = env.get(k3);

        assertThat(replaceEnvVariables("${" + k1 + "} is missing!"), is(v1 + " is missing!"));
        assertThat(replaceEnvVariables("You are the ${" + k2 + "}"), is("You are the " + v2));
        assertThat(replaceEnvVariables("${" + k3 + "}"), is(v3));
        assertThat(replaceEnvVariables("as is $"), is("as is $"));
    }

}