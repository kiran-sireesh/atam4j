package me.atam.atam4j.health;

import me.atam.atam4j.health.testsupport.AppWithTestFailuresUsingAtam4jRule;
import org.eclipse.jetty.http.HttpStatus;
import org.junit.ClassRule;
import org.junit.Test;

import javax.ws.rs.core.Response;

import static org.junit.Assert.assertEquals;

public class AcceptanceTestsHealthCheckForFailuresIT {

    @ClassRule
    public static final AppWithTestFailuresUsingAtam4jRule appRule = new AppWithTestFailuresUsingAtam4jRule();

    @Test
    public void healthCheckReportsTestFailureDetails() {
        final Response response = appRule.healthcheckResourceTarget()
                .request()
                .get();
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR_500, response.getStatus());
        String responseString = response.readEntity(String.class);
        //TODO: parse the json embedded in the respose and assert for testFailuresCount and exceptoin details
    }
}
