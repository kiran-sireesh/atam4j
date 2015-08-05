package me.atam.atam4j.health;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.junit.runner.notification.Failure;

import java.util.List;
import java.util.stream.Collectors;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class TestRunReport {

    @JsonProperty("totalTestsRun")
    private final int totalTests;
    @JsonProperty("totalFailures")
    private final int totalFailures;
    @JsonProperty("failedTests")
    private final List<FailedTest> failedTests;

    public TestRunReport(int totalFailures, int totalTests, List<Failure> failures) {
        this.totalFailures = totalFailures;
        this.totalTests = totalTests;

        this.failedTests = failures.stream()
                .map(failure -> new FailedTest(failure.getTestHeader(), failure.getMessage()))
                .collect(Collectors.toList());
    }

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private class FailedTest {
        @JsonProperty("test")
        private final String test;
        @JsonProperty("cause")
        private final String cause;

        public FailedTest(String test, String cause) {
            this.test = test;
            this.cause = cause;
        }

        public String getCause() {
            return cause;
        }

        public String getTest() {
            return test;
        }
    }

}
