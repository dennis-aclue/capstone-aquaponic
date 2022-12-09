package de.voelkldennis.backend.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class UserDTOTest {

    @Test
    void testEquals() {
        // Arrange, Act and Assert
        assertNotEquals("42", new UserDTO("FirstName", "LastName", "Username", "Email", "Password"));
    }

    @Test
    void testHashCode() {
        // Arrange, Act and Assert
        assertEquals(0, (new UserDTO("FirstName", "LastName", "Username", "Email", "Password")).hashCode());
    }

    @Test
    void testToString() {
        // Arrange, Act and Assert
        assertEquals("null", (new UserDTO("FirstName", "LastName", "Username", "Email", "Password")).toString());
    }

    @Test
    void firstName() {
        // Arrange, Act and Assert
        assertEquals("FirstName", (new UserDTO("FirstName", "LastName", "Username", "Email", "Password")).firstName());
    }

    @Test
    void lastName() {
        // Arrange, Act and Assert
        assertEquals("LastName", (new UserDTO("FirstName", "LastName", "Username", "Email", "Password")).lastName());
    }

    @Test
    void username() {
        // Arrange, Act and Assert
        assertEquals("Username", (new UserDTO("FirstName", "LastName", "Username", "Email", "Password")).username());
    }

    @Test
    void email() {
        // Arrange, Act and Assert
        assertEquals("Email", (new UserDTO("FirstName", "LastName", "Username", "Email", "Password")).email());
    }

    @Test
    void password() {
        // Arrange, Act and Assert
        assertEquals("Password", (new UserDTO("FirstName", "LastName", "Username", "Email", "Password")).password());
    }
}