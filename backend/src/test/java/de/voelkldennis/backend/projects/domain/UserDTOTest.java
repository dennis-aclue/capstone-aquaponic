package de.voelkldennis.backend.projects.domain;

import de.voelkldennis.backend.domain.UserDTO;
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