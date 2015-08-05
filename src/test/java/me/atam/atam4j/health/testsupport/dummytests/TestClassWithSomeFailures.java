package me.atam.atam4j.health.testsupport.dummytests;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class TestClassWithSomeFailures {

    @Test
    public void passingTest() {
        assertTrue(true);
    }

    @Test
    public void failingTest() {
        assertTrue(false);
    }
}
