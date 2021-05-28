package com.univtln.univTlnLPS.model.administration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;

import org.junit.jupiter.api.Test;

public class FormDevenirSuperTest {
    @Test
    public void testSetPasswordHash() throws NoSuchAlgorithmException, InvalidKeySpecException {
        FormDevenirSuper formDevenirSuper = new FormDevenirSuper();
        formDevenirSuper.setPasswordHash("iloveyou");
        assertEquals(Short.SIZE, formDevenirSuper.getPasswordHash().length);
    }

    @Test
    public void testCanEqual() {
        assertFalse((new FormDevenirSuper()).canEqual("Other"));
    }

    @Test
    public void testCanEqual2() {
        FormDevenirSuper formDevenirSuper = new FormDevenirSuper();
        assertTrue(formDevenirSuper.canEqual(new FormDevenirSuper()));
    }

    @Test
    public void testConstructor() throws UnsupportedEncodingException {
        FormDevenirSuper actualFormDevenirSuper = new FormDevenirSuper();
        actualFormDevenirSuper.setEmail("jane.doe@example.org");
        actualFormDevenirSuper.setId(123L);
        SecureRandom secureRandom = new SecureRandom();
        actualFormDevenirSuper.setRandom(secureRandom);
        actualFormDevenirSuper.setSalt("AAAAAAAA".getBytes("UTF-8"));
        assertEquals("jane.doe@example.org", actualFormDevenirSuper.getEmail());
        assertEquals(123L, actualFormDevenirSuper.getId());
        assertNull(actualFormDevenirSuper.getPasswordHash());
        assertSame(secureRandom, actualFormDevenirSuper.getRandom());
        assertEquals("FormDevenirSuper(id=123, email=jane.doe@example.org, passwordHash=null, salt=[65, 65, 65, 65, 65, 65,"
                + " 65, 65], ", actualFormDevenirSuper.toString().split("random")[0]);
    }

    @Test
    public void testConstructor2() {
        FormDevenirSuper actualFormDevenirSuper = new FormDevenirSuper();
        assertEquals("FormDevenirSuper(id=0, email=null, passwordHash=null, salt=[0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,"
                + " 0, 0], ", actualFormDevenirSuper.toString().split("random")[0]);
        assertEquals(Short.SIZE, actualFormDevenirSuper.getSalt().length);
    }

    @Test
    public void testConstructor3() throws UnsupportedEncodingException {
        byte[] passwordHash = "AAAAAAAAAAAAAAAAAAAAAAAA".getBytes("UTF-8");
        byte[] salt = "AAAAAAAAAAAAAAAAAAAAAAAA".getBytes("UTF-8");
        FormDevenirSuper actualFormDevenirSuper = new FormDevenirSuper(123L, "jane.doe@example.org", passwordHash, salt,
                new SecureRandom());
        assertEquals("jane.doe@example.org", actualFormDevenirSuper.getEmail());
        assertEquals(
                "FormDevenirSuper(id=123, email=jane.doe@example.org, passwordHash=[65, 65, 65, 65, 65, 65, 65, 65, 65,"
                        + " 65, 65, 65, 65, 65, 65, 65, 65, 65, 65, 65, 65, 65, 65, 65], salt=[65, 65, 65, 65, 65, 65, 65, 65,"
                        + " 65, 65, 65, 65, 65, 65, 65, 65, 65, 65, 65, 65, 65, 65, 65, 65], ",
                actualFormDevenirSuper.toString().split("random")[0]);
        assertEquals(24, actualFormDevenirSuper.getSalt().length);
        assertEquals(24, actualFormDevenirSuper.getPasswordHash().length);
        assertEquals(123L, actualFormDevenirSuper.getId());
    }

    @Test
    public void testEquals() {
        assertFalse((new FormDevenirSuper()).equals("42"));
    }

    @Test
    public void testEquals2() {
        FormDevenirSuper formDevenirSuper = new FormDevenirSuper();
        assertTrue(formDevenirSuper.equals(new FormDevenirSuper()));
    }

    @Test
    public void testEquals3() throws UnsupportedEncodingException {
        FormDevenirSuper formDevenirSuper = new FormDevenirSuper();
        byte[] passwordHash = "AAAAAAAAAAAAAAAAAAAAAAAA".getBytes("UTF-8");
        byte[] salt = "AAAAAAAAAAAAAAAAAAAAAAAA".getBytes("UTF-8");
        assertFalse(formDevenirSuper
                .equals(new FormDevenirSuper(123L, "jane.doe@example.org", passwordHash, salt, new SecureRandom())));
    }

    @Test
    public void testHashCode() {
        assertEquals(59, (new FormDevenirSuper()).hashCode());
    }
}

