package com.btmatthews.hadoopunit;

import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

public class HadoopTesterRule implements TestRule {

    private HadoopConfiguration annotation;

    /**
     * Modifies the method-running {@link Statement} to implement this test-running rule. The configuration for
     * the embedded LDAP directory server is obtained from the {@link HadoopConfiguration} annotation that was
     * applied to either the test class or test method.
     *
     * @param base        The {@link Statement} to be modified
     * @param description A {@link Description} of the test implemented in {@code base}
     * @return If no configuration was found then {@code base} is returned. Otherwise, a new
     *         {@link HadoopStatement} that wraps {@code base} is returned.
     */
    @Override
    public Statement apply(final Statement base, final Description description) {
        annotation = description.getAnnotation(HadoopConfiguration.class);
        if (annotation == null) {
            final Class testClass = description.getTestClass();
            annotation = (HadoopConfiguration) testClass.getAnnotation(HadoopConfiguration.class);
        }
        if (annotation != null) {
            return new HadoopStatement(base, annotation);
        }
        return base;
    }

    public HadoopTester getTester() {
        return null;
    }
}
