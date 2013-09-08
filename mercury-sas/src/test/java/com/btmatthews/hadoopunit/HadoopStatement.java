package com.btmatthews.hadoopunit;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.junit.runners.model.Statement;

public class HadoopStatement extends Statement {

    private final Statement base;
    private final HadoopConfiguration annotation;

    public HadoopStatement(final Statement base, final HadoopConfiguration annotation) {
        this.base = base;
        this.annotation = annotation;
    }

    @Override
    public void evaluate() throws Throwable {
        final Configuration configuration = new Configuration();
        for (final String configurationFile : annotation.value()) {
            configuration.addResource(new Path(configurationFile));
        }
    }

}
