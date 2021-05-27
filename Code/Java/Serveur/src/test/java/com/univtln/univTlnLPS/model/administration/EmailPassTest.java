package com.univtln.univTlnLPS.model.administration;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class EmailPassTest {
    @Test
    public void testBuilder() {
        // TODO: This test is incomplete.
        //   Reason: No meaningful assertions found.
        //   To help Diffblue Cover to find assertions, please add getters to the
        //   class under test that return fields written by the method under test.
        //   See https://diff.blue/R004

        EmailPass.builder();
    }

    @Test
    public void testConstructor() {
        EmailPass actualEmailPass = new EmailPass();
        actualEmailPass.setEmail("jane.doe@example.org");
        actualEmailPass.setPassword("iloveyou");
        assertEquals("jane.doe@example.org", actualEmailPass.getEmail());
        assertEquals("iloveyou", actualEmailPass.getPassword());
    }

    @Test
    public void testConstructor2() {
        EmailPass actualEmailPass = new EmailPass("jane.doe@example.org", "iloveyou");
        actualEmailPass.setEmail("jane.doe@example.org");
        actualEmailPass.setPassword("iloveyou");
        assertEquals("jane.doe@example.org", actualEmailPass.getEmail());
        assertEquals("iloveyou", actualEmailPass.getPassword());
    }
}

