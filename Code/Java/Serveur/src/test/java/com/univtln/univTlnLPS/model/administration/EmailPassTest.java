package com.univtln.univTlnLPS.model.administration;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class EmailPassTest {

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

