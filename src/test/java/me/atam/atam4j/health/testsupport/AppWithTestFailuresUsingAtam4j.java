package me.atam.atam4j.health.testsupport;

import io.dropwizard.Application;
import io.dropwizard.Configuration;
import io.dropwizard.setup.Environment;
import me.atam.atam4j.Atam4j;
import me.atam.atam4j.health.testsupport.dummytests.TestClassWithSomeFailures;

public class AppWithTestFailuresUsingAtam4j extends Application<Configuration> {

    @Override
    public void run(Configuration configuration, Environment environment) throws Exception {
        new Atam4j.Atam4jBuilder(environment)
                .withTestClasses(TestClassWithSomeFailures.class)
                .withInitialDelay(0)
                .build()
                .initialise();
    }


}