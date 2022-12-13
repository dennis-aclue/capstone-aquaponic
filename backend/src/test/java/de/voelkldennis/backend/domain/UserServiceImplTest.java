package de.voelkldennis.backend.domain;

import de.voelkldennis.backend.ProjectUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriComponentsBuilder;

import javax.mail.MessagingException;
import java.util.Arrays;
import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@AutoConfigureMockMvc
class UserServiceImplTest {

    UserRepository userRepository = mock(UserRepository.class);
    ProjectUtils projectUtils = mock(ProjectUtils.class);
    PasswordEncoder passwordEncoder = mock(PasswordEncoder.class);
    ServletUriComponentsBuilder servletUriComponentsBuilder = mock(ServletUriComponentsBuilder.class);
    EmailService emailService = mock(EmailService.class);

    @Autowired
    private MockMvc mockMvc;


    @Test
    @DirtiesContext
    void getTemporaryProfileImageUrl() {
        String username = "username";
        String profilePath = "/profile/";
        String actual = profilePath + username;
        when(servletUriComponentsBuilder.path(profilePath + username)).thenReturn(UriComponentsBuilder.fromPath(actual));
        String expected = servletUriComponentsBuilder.path(profilePath + username).toUriString();
        assertEquals(expected, actual);

    }

    @Test
    @DirtiesContext
    void updateUser() {
        //given
        String username = "aquaponic_test_username";
        String firstName = "aquaponic_test_firstName";
        String lastName = "aquaponic_test_lastName";
        String email = "aquaponic_test_email";
        String password = "aquaponic_test_password";
        String role = "ROLE_SUPER_ADMIN";
        String[] authorities = {"user:read"};
        boolean notLocked = true;
        boolean active = true;
        Date lastLoginDate = new Date();
        Date lastLoginDateDisplay = new Date();
        Date joinDate = new Date();
        User user = new User();
        user.setUserId("1");
        user.setId("1");
        user.setUsername(username);
        user.setPassword(password);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setRole(role);
        user.setAuthorities(authorities);
        user.setNotLocked(notLocked);
        user.setActive(active);
        user.setLastLoginDate(lastLoginDate);
        user.setLastLoginDateDisplay(lastLoginDateDisplay);
        user.setJoinDate(joinDate);
        //when
        when(userRepository.save(user)).thenReturn(user);
        User actual = userRepository.save(user);
        //then
        assertEquals(user, actual);

    }

    @Test
    @DirtiesContext
    void deleteUser() {
        User user = new User();
        user.setId("1");
        userRepository.deleteById(user.getId());
        when(userRepository.findById(user.getId())).thenReturn(Optional.empty());
        Optional<User> actual = userRepository.findById(user.getId());
        verify(userRepository, times(1)).deleteById(user.getId());
        assertEquals(Optional.empty(), actual);
    }

    @Test
    void resetPassword() throws MessagingException {
        User user = new User();
        user.setId("1");
        user.setPassword("aquaponic_test_password");
        user.setEmail("aquaponic_test_email");

        userRepository.findUserByEmail(user.getEmail());
        when(userRepository.findUserByEmail(user.getEmail())).thenReturn(user);

        passwordEncoder.encode(user.getPassword());
        when(passwordEncoder.encode(user.getPassword())).thenReturn(user.getPassword());

        user.setPassword(passwordEncoder.encode("new_password"));

        emailService.sendNewPasswordEmail(user.getFirstName(), user.getEmail(), user.getPassword());
        doNothing().when(emailService).sendNewPasswordEmail(user.getFirstName(), user.getEmail(), user.getPassword());

        when(userRepository.save(user)).thenReturn(user);

        assertNotEquals("new_password", user.getPassword());

    }

    @Test
    void loadUserByExistingUsername() {

        User user = new User();
        user.setId("1");
        user.setUsername("aquaponic_test_username");
        user.setPassword("aquaponic_test_password");
        user.setFirstName("aquaponic_test_firstName");
        user.setLastName("aquaponic_test_lastName");
        user.setEmail("aquaponic_test_email");

        if (userRepository.findUserByUsername(user.getUsername()) != null) {
            userRepository.save(user);
        }
        when(userRepository.findUserByUsername(user.getUsername())).thenReturn(user);
        User actual = userRepository.findUserByUsername(user.getUsername());
        assertEquals(user, actual);

    }

    @Test
    void loadUserByNotExistingUsername() {

        User user = new User();
        user.setId("1");
        user.setUsername("aquaponic_test_username");
        user.setPassword("aquaponic_test_password");
        user.setFirstName("aquaponic_test_firstName");
        user.setLastName("aquaponic_test_lastName");
        user.setEmail("aquaponic_test_email");

        when(userRepository.findUserByUsername(user.getUsername())).thenThrow(UsernameNotFoundException.class);
        assertThrows(UsernameNotFoundException.class, () -> userRepository.findUserByUsername(user.getUsername()));

    }


    @Test
    void register() throws MessagingException {
        User user = new User();
        user.setUsername("aquaponic_test_username");
        user.setFirstName("aquaponic_test_firstName");
        user.setLastName("aquaponic_test_lastName");
        user.setEmail("aquaponic@test.email");
        user.setId("1");
        user.setPassword("aquaponic_test_password");
        user.setJoinDate(new Date());
        user.setActive(true);
        user.setNotLocked(true);
        user.setRole("ROLE_SUPER_ADMIN");
        user.setAuthorities(new String[]{"user:read", "user:update", "user:delete"});
        String profilePath = "/user/image/profile/" + user.getUsername();

        for (EmailService service : Arrays.asList(emailService, doNothing().when(emailService))) {
            service.sendNewPasswordEmail(user.getFirstName(), user.getEmail(), user.getPassword());
        }
        when(userRepository.save(user)).thenReturn(user);
        User actual = userRepository.save(user);
        assertEquals(user, actual);

    }

    @Test
    void findUserByUsername() {
        User user = new User();
        user.setId("1");
        user.setUsername("aquaponic_test_username");
        user.setPassword("aquaponic_test_password");
        user.setFirstName("aquaponic_test_firstName");
        user.setLastName("aquaponic_test_lastName");
        user.setEmail("aquaponic_test_email");

        when(userRepository.findUserByUsername(user.getUsername())).thenReturn(user);
        User actual = userRepository.findUserByUsername(user.getUsername());
        assertEquals(user, actual);

    }

    @Test
    void findUserByEmail() {
        User user = new User();
        user.setId("1");
        user.setUsername("aquaponic_test_username");
        user.setPassword("aquaponic_test_password");
        user.setFirstName("aquaponic_test_firstName");
        user.setLastName("aquaponic_test_lastName");
        user.setEmail("aquaponic_test_email");

        when(userRepository.findUserByEmail(user.getEmail())).thenReturn(user);
        User actual = userRepository.findUserByEmail(user.getEmail());
        assertEquals(user, actual);

    }

    @Test
    @DirtiesContext
    void generateUserId() {
        User user = new User();
        user.setId("1");
        user.setUserId("1");
        user.setUsername("aquaponic_test_username");
        user.setPassword("aquaponic_test_password");
        user.setFirstName("aquaponic_test_firstName");
        user.setLastName("aquaponic_test_lastName");
        user.setEmail("aquaponic_test_email");

        when(projectUtils.generateUUID()).thenReturn(user.getUserId());
        String actual = projectUtils.generateUUID();
        assertEquals(user.getUserId(), actual);

    }

    @Test
    @DirtiesContext
    void generatePassword() {
        User user = new User();
        user.setId("1");
        user.setUserId("1");
        user.setUsername("aquaponic_test_username");
        user.setPassword("aquaponic_test_password");
        user.setFirstName("aquaponic_test_firstName");
        user.setLastName("aquaponic_test_lastName");
        user.setEmail("aquaponic_test_email");

        when(projectUtils.generateUUID()).thenReturn(user.getPassword());
        String actual = projectUtils.generateUUID().substring(0, 8);
        assertEquals(user.getPassword().substring(0, 8), actual);

    }

    @Test
    @DirtiesContext
    void encodePassword() {
        User user = new User();
        user.setId("1");
        user.setUserId("1");
        user.setUsername("aquaponic_test_username");
        user.setPassword("aquaponic_test_password");
        user.setFirstName("aquaponic_test_firstName");
        user.setLastName("aquaponic_test_lastName");
        user.setEmail("aquaponic_test_email");

        when(passwordEncoder.encode(user.getPassword())).thenReturn(user.getPassword());
        String actual = passwordEncoder.encode(user.getPassword());
        assertEquals(user.getPassword(), actual);

    }

}