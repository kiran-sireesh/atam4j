package me.atam.atam4j;

import me.atam.atam4j.health.AcceptanceTestsState;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

public class AcceptanceTestsRunnerTask implements Runnable {

    private static final Logger LOGGER = LoggerFactory.getLogger(AcceptanceTestsRunnerTask.class);

    private final AcceptanceTestsState testsState;
    private final Class[] testClasses;

    AcceptanceTestsRunnerTask(final AcceptanceTestsState testsState,
                              final Class... testClasses) {
        this.testsState = testsState;
        this.testClasses = testClasses;
    }

    @Override
    public void run() {
        LOGGER.info("Starting tests at {}", new Date());

        Result result = JUnitCore.runClasses(testClasses);
        testsState.setResult(result);

        LOGGER.info("Tests finishes at {}", new Date());
        LOGGER.info("Report :: total run = {}, failures = {}, in time = {} milliseconds",
                result.getRunCount(),
                result.getFailureCount(),
                result.getRunTime()
        );

        for (Failure failure: result.getFailures()) {
            LOGGER.error(failure.getDescription().toString(), failure.getException());
        }
    }
}
