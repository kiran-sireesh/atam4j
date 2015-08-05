package me.atam.atam4j.health;

import me.atam.atam4j.health.testsupport.AppUsingAtam4jRule;
import org.eclipse.jetty.http.HttpStatus;
import org.junit.ClassRule;
import org.junit.Test;

import javax.ws.rs.core.Response;

import static org.junit.Assert.assertEquals;

public class AcceptanceTestsHealthCheckIT {

    @ClassRule
    public static final AppUsingAtam4jRule appRule = new AppUsingAtam4jRule();

    @Test
    public void healthCheckWithNoTestFailuresReturns200() {
        final Response response = appRule.healthcheckResourceTarget()
                .request()
                .get();
        assertEquals(HttpStatus.OK_200, response.getStatus());
        String s = response.readEntity(String.class);
        System.out.println(s);
    }
}
