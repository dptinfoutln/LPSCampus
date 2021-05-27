package com.univtln.univTlnLPS.model.administration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import org.junit.jupiter.api.Test;

public class BugReportTest {
    @Test
    public void testBuilder() {
        // TODO: This test is incomplete.
        //   Reason: No meaningful assertions found.
        //   To help Diffblue Cover to find assertions, please add getters to the
        //   class under test that return fields written by the method under test.
        //   See https://diff.blue/R004

        BugReport.builder();
    }

    @Test
    public void testCanEqual() {
        assertFalse((new BugReport()).canEqual("Other"));
    }

    @Test
    public void testCanEqual2() {
        BugReport bugReport = new BugReport();
        assertTrue(bugReport.canEqual(new BugReport()));
    }

    @Test
    public void testConstructor() {
        BugReport actualBugReport = new BugReport();
        actualBugReport.setCaracteristiquesMachine("Caracteristiques Machine");
        actualBugReport.setCategory("Category");
        actualBugReport.setContent("Not all who wander are lost");
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        Date fromResult = Date.from(atStartOfDayResult.atZone(ZoneId.systemDefault()).toInstant());
        actualBugReport.setDate(fromResult);
        actualBugReport.setId(123L);
        assertEquals("Caracteristiques Machine", actualBugReport.getCaracteristiquesMachine());
        assertEquals("Category", actualBugReport.getCategory());
        assertEquals("Not all who wander are lost", actualBugReport.getContent());
        assertSame(fromResult, actualBugReport.getDate());
        assertEquals(123L, actualBugReport.getId());
        assertEquals("BugReport(id=123, category=Category, content=Not all who wander are lost, caracteristiquesMachine"
                + "=Caracteristiques Machine, date=Thu Jan 01 00:00:00 CET 1970)", actualBugReport.toString());
    }

    @Test
    public void testConstructor2() {
        BugReport actualBugReport = new BugReport(123L, "Category", "Not all who wander are lost",
                "Caracteristiques Machine", new Date(1L));
        assertEquals("Caracteristiques Machine", actualBugReport.getCaracteristiquesMachine());
        assertEquals("BugReport(id=123, category=Category, content=Not all who wander are lost, caracteristiquesMachine"
                + "=Caracteristiques Machine, date=Thu Jan 01 01:00:00 CET 1970)", actualBugReport.toString());
        assertEquals(123L, actualBugReport.getId());
        assertEquals("Not all who wander are lost", actualBugReport.getContent());
        assertEquals("Category", actualBugReport.getCategory());
    }

    @Test
    public void testEquals() {
        assertFalse((new BugReport()).equals("42"));
    }

    @Test
    public void testEquals2() {
        BugReport bugReport = new BugReport();
        assertTrue(bugReport.equals(new BugReport()));
    }

    @Test
    public void testEquals3() {
        BugReport bugReport = new BugReport();
        assertFalse(bugReport.equals(
                new BugReport(123L, "Category", "Not all who wander are lost", "Caracteristiques Machine", new Date(1L))));
    }

    @Test
    public void testHashCode() {
        assertEquals(59, (new BugReport()).hashCode());
    }
}

