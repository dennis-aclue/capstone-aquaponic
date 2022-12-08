package de.voelkldennis.backend.domain;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
class UserTest {

    User user = new User();

    @Test
    @DirtiesContext
    void getId() {
        user.setId("test_id");
        String actual = user.getId();
        String expected = "test_id";
        assertEquals(expected, actual);
    }

    @Test
    @DirtiesContext
    void setId() {
        user.setId("test_id");
        String actual = user.getId();
        String expected = "test_id";
        assertEquals(expected, actual);
    }

    @Test
    @DirtiesContext
    void getUserId() {
        user.setUsername("test_username");
        String actual = user.getUsername();
        String expected = "test_username";
        assertEquals(expected, actual);
    }

    @Test
    @DirtiesContext
    void setUserId() {
        user.setUsername("test_username");
        String actual = user.getUsername();
        String expected = "test_username";
        assertEquals(expected, actual);
    }

    @Test
    @DirtiesContext
    void getFirstName() {
        user.setFirstName("test_firstname");
        String actual = user.getFirstName();
        String expected = "test_firstname";
        assertEquals(expected, actual);
    }

    @Test
    @DirtiesContext
    void setFirstName() {
        user.setFirstName("test_firstname");
        String actual = user.getFirstName();
        String expected = "test_firstname";
        assertEquals(expected, actual);
    }

    @Test
    @DirtiesContext
    void getLastName() {
        user.setLastName("test_lastname");
        String actual = user.getLastName();
        String expected = "test_lastname";
        assertEquals(expected, actual);
    }

    @Test
    @DirtiesContext
    void setLastName() {
        user.setLastName("test_lastname");
        String actual = user.getLastName();
        String expected = "test_lastname";
        assertEquals(expected, actual);
    }

    @Test
    @DirtiesContext
    void getUsername() {
        user.setUsername("test_username");
        String actual = user.getUsername();
        String expected = "test_username";
        assertEquals(expected, actual);
    }

    @Test
    @DirtiesContext
    void setUsername() {
        user.setUsername("test_username");
        String actual = user.getUsername();
        String expected = "test_username";
        assertEquals(expected, actual);
    }

    @Test
    @DirtiesContext
    void getPassword() {
        user.setPassword("test_password");
        String actual = user.getPassword();
        String expected = "test_password";
        assertEquals(expected, actual);
    }

    @Test
    @DirtiesContext
    void setPassword() {
        user.setPassword("test_password");
        String actual = user.getPassword();
        String expected = "test_password";
        assertEquals(expected, actual);
    }

    @Test
    @DirtiesContext
    void getEmail() {
        user.setEmail("test_email");
        String actual = user.getEmail();
        String expected = "test_email";
        assertEquals(expected, actual);
    }

    @Test
    @DirtiesContext
    void setEmail() {
        user.setEmail("test_email");
        String actual = user.getEmail();
        String expected = "test_email";
        assertEquals(expected, actual);
    }

    @Test
    @DirtiesContext
    void getProfileImageUrl() {
        user.setProfileImageUrl("test_profileImageUrl");
        String actual = user.getProfileImageUrl();
        String expected = "test_profileImageUrl";
        assertEquals(expected, actual);
    }

    @Test
    @DirtiesContext
    void setProfileImageUrl() {
        user.setProfileImageUrl("test_profileImageUrl");
        String actual = user.getProfileImageUrl();
        String expected = "test_profileImageUrl";
        assertEquals(expected, actual);
    }

    @Test
    @DirtiesContext
    void getLastLoginDate() {
        Date date = mock(Date.class);
        when(date.getTime()).thenReturn(123456789L);
        user.setLastLoginDate(date);
        Date actual = user.getLastLoginDate();
        Date expected = date;
        assertEquals(expected, actual);
    }

    @Test
    @DirtiesContext
    void setLastLoginDate() {
        Date date = mock(Date.class);
        when(date.getTime()).thenReturn(123456789L);
        user.setLastLoginDate(date);
        Date actual = user.getLastLoginDate();
        Date expected = date;
        assertEquals(expected, actual);
    }

    @Test
    @DirtiesContext
    void getLastLoginDateDisplay() {
        Date date = mock(Date.class);
        when(date.getTime()).thenReturn(123456789L);
        user.setLastLoginDateDisplay(date);
        Date actual = user.getLastLoginDateDisplay();
        Date expected = date;
        assertEquals(expected, actual);
    }

    @Test
    @DirtiesContext
    void setLastLoginDateDisplay() {
        Date date = mock(Date.class);
        when(date.getTime()).thenReturn(123456789L);
        user.setLastLoginDateDisplay(date);
        Date actual = user.getLastLoginDateDisplay();
        Date expected = date;
        assertEquals(expected, actual);
    }

    @Test
    @DirtiesContext
    void getJoinDate() {
        Date date = mock(Date.class);
        when(date.getTime()).thenReturn(123456789L);
        user.setJoinDate(date);
        Date actual = user.getJoinDate();
        Date expected = date;
        assertEquals(expected, actual);
    }

    @Test
    @DirtiesContext
    void setJoinDate() {
        Date date = mock(Date.class);
        when(date.getTime()).thenReturn(123456789L);
        user.setJoinDate(date);
        Date actual = user.getJoinDate();
        Date expected = date;
        assertEquals(expected, actual);
    }

    @Test
    @DirtiesContext
    void getRole() {
        user.setRole("test_role");
        String actual = user.getRole();
        String expected = "test_role";
        assertEquals(expected, actual);
    }

    @Test
    @DirtiesContext
    void setRole() {
        user.setRole("test_role");
        String actual = user.getRole();
        String expected = "test_role";
        assertEquals(expected, actual);
    }

    @Test
    @DirtiesContext
    void getAuthorities() {
        String[] authorities = {"test_authority"};
        user.setAuthorities(authorities);
        String[] actual = user.getAuthorities();
        String[] expected = authorities;
        assertArrayEquals(expected, actual);
    }

    @Test
    @DirtiesContext
    void setAuthorities() {
        String[] authorities = {"test_authority"};
        user.setAuthorities(authorities);
        String[] actual = user.getAuthorities();
        String[] expected = authorities;
        assertArrayEquals(expected, actual);
    }

    @Test
    @DirtiesContext
    void isActive() {
        user.setActive(true);
        boolean actual = user.isActive();
        boolean expected = true;
        assertEquals(expected, actual);
    }

    @Test
    @DirtiesContext
    void setActive() {
        user.setActive(true);
        boolean actual = user.isActive();
        boolean expected = true;
        assertEquals(expected, actual);
    }

    @Test
    @DirtiesContext
    void isNotLocked() {
        user.setNotLocked(true);
        boolean actual = user.isNotLocked();
        boolean expected = true;
        assertEquals(expected, actual);
    }

    @Test
    @DirtiesContext
    void setNotLocked() {
        user.setNotLocked(true);
        boolean actual = user.isNotLocked();
        boolean expected = true;
        assertEquals(expected, actual);
    }
}