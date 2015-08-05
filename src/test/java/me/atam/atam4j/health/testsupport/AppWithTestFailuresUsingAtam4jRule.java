package me.atam.atam4j.health.testsupport;

import io.dropwizard.Configuration;
import io.dropwizard.testing.junit.DropwizardAppRule;
import org.glassfish.jersey.filter.LoggingFilter;
import org.junit.ClassRule;
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;

public class AppWithTestFailuresUsingAtam4jRule implements TestRule {

    @ClassRule
    public static final DropwizardAppRule<Configuration> dropwizardAppRule = new DropwizardAppRule<>(AppWithTestFailuresUsingAtam4j.class, null);
    private static final Client client = ClientBuilder.newBuilder().register(LoggingFilter.class).build();

    @Override
    public Statement apply(Statement base, Description description) {
        return dropwizardAppRule.apply(base, description);
    }

    public WebTarget healthcheckResourceTarget() {
        return client.target("http://localhost:" + dropwizardAppRule.getAdminPort() + "/healthcheck");
    }

}