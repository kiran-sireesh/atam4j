package me.atam.atam4j;

import com.google.common.base.Preconditions;
import io.dropwizard.setup.Environment;
import me.atam.atam4j.health.AcceptanceTestsHealthCheck;
import me.atam.atam4j.health.AcceptanceTestsState;
import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.scanners.TypeAnnotationsScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

public class Atam4j {

    private final Environment environment;
    private final AcceptanceTestsState acceptanceTestsState = new AcceptanceTestsState();
    private final AcceptanceTestsRunnerTaskScheduler acceptanceTestsRunnerTaskScheduler;

    public Atam4j(final AcceptanceTestsRunnerTaskScheduler acceptanceTestsRunnerTaskScheduler,
                  final Environment environment) {
        this.acceptanceTestsRunnerTaskScheduler = acceptanceTestsRunnerTaskScheduler;
        this.environment = environment;
    }

    public void initialise() {
        acceptanceTestsRunnerTaskScheduler.scheduleAcceptanceTestsRunnerTask(acceptanceTestsState);
        AcceptanceTestsHealthCheck healthCheck = new AcceptanceTestsHealthCheck(acceptanceTestsState);
        environment.healthChecks().register(AcceptanceTestsHealthCheck.NAME, healthCheck);
    }

    public static class Atam4jBuilder {

        private Environment environment;
        private Optional<Class[]> testClasses = Optional.empty();
        private long initialDelay = 60;
        private long period = 300;
        private TimeUnit unit = TimeUnit.SECONDS;

        public Atam4jBuilder(Environment environment) {
            this.environment = Preconditions.checkNotNull(environment);
        }

        public Atam4jBuilder withTestClasses(Class... testClasses) {
            this.testClasses = Optional.of(testClasses);
            return this;
        }

        public Atam4jBuilder withInitialDelay(long initialDelay) {
            this.initialDelay = initialDelay;
            return this;
        }

        public Atam4jBuilder withPeriod(long period) {
            this.period = period;
            return this;
        }

        public Atam4jBuilder withUnit(TimeUnit unit) {
            this.unit = unit;
            return this;
        }

        public Atam4j build() {
            return new Atam4j(
                    new AcceptanceTestsRunnerTaskScheduler(
                        environment,
                        findTestClasses(),
                        initialDelay,
                        period,
                        unit),
                    this.environment);
        }

        private Class[] findTestClasses() {
            final Class[] classes = testClasses.orElseGet(() ->
                    new Reflections(new ConfigurationBuilder()
                            .setUrls(ClasspathHelper.forJavaClassPath())
                            .setScanners(new SubTypesScanner(), new TypeAnnotationsScanner()))
                            .getTypesAnnotatedWith(Monitor.class)
                            .stream()
                            .toArray(Class[]::new));
            if(classes.length == 0) {
                throw new NoTestClassFoundException("Could not find any annotated test classes and no classes were provided via the Atam4jBuilder.");
            }
            return classes;
        }
    }
}