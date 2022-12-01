package de.voelkldennis.backend.domain;

import de.voelkldennis.backend.domain.enumeration.Role;
import de.voelkldennis.backend.domain.enumeration.UserServiceImpl;
import de.voelkldennis.backend.exception.domain.EmailExistException;
import de.voelkldennis.backend.exception.domain.UsernameExistException;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Arrays.stream;

@RequiredArgsConstructor
@Service("userDetailsService")
@Transactional
public class UserService implements UserDetails, UserDetailsService, UserServiceImpl {
    private UserRepo userRepo;
    private Logger LOGGER = LoggerFactory.getLogger(getClass());
    public static BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    private User user;

    @Autowired
    public UserService(UserRepo userRepo, BCryptPasswordEncoder passwordEncoder) {
        this.user = user;
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
    }

    public void deleteUser(String username) {
        userRepo.deleteByUsername(username);
    }

    public void createUser(User user) {
        userRepo.save(user);
    }

    public void updateUser(User user) {
        userRepo.save(user);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return stream(this.user.getAuthorities()).map(SimpleGrantedAuthority::new).collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return this.user.getPassword();
    }

    @Override
    public String getUsername() {
        return this.user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        // I do not use this method in my application now
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.user.isNotLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        // I do not use this method too (I do not use password expiration)
        return true;
    }

    @Override
    public boolean isEnabled() {
        return this.user.isActive();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.findByUsername(username);
        if (user == null) {
            LOGGER.error("User not found by username: " + username);
            throw new UsernameNotFoundException("User not found by username: " + username);
        } else {
            user.setLastLoginDateDisplay(user.getLastLoginDate());
            user.setLastLoginDate(new Date());
            userRepo.save(user);
            UserService userService = new UserService();
            LOGGER.info("Returning found user by username: " + username);
            return userService;
        }
    }

    @Override
    public User register(String firstName,
                         String lastName,
                         String username,
                         String email)
            throws EmailExistException,
            UsernameExistException,
            UsernameNotFoundException {

        validateNewUsernameAndEmail(StringUtils.EMPTY, username, email);

        User appUser = new User();
        appUser.setId(generateUserId());
        appUser.setUserId(generateUserId());
        String password = generatePassword();
        String encodedPassword = encodePassword(password);
        appUser.setFirstName(firstName);
        appUser.setLastName(lastName);
        appUser.setUsername(username);
        appUser.setEmail(email);
        appUser.setJoinDate(new Date());
        appUser.setPassword(encodedPassword);
        appUser.setActive(true);
        appUser.setNotLocked(true);
        appUser.setRole(Role.ROLE_USER.name());
        appUser.setAuthorities(Role.ROLE_USER.getAuthorities());
        appUser.setProfileImageUrl(getTemporaryProfileImageUrl(username));
        userRepo.save(appUser);
        // For now to test the registration, I will log the password in the console
        //LOGGER.info("New user password: " + password);

        return null;
    }

    private String getTemporaryProfileImageUrl(String username) {
        // for later configuration of the profile image
        return ServletUriComponentsBuilder.fromCurrentContextPath().path("/user/image/profile/temp").toUriString();
        //return "https://robohash.org/" + username + "?set=set3&size=180x180";
    }

    private String encodePassword(String password) {
        return passwordEncoder.encode(password);
    }

    private String generatePassword() {
        return RandomStringUtils.randomAlphanumeric(10);
    }

    private String generateUserId() {
        return RandomStringUtils.randomNumeric(10);
    }

    private User validateNewUsernameAndEmail(String currentUsername,
                                             String newUsername,
                                             String newEmail)
            throws UsernameExistException,
            EmailExistException,
            UsernameNotFoundException {

        if (StringUtils.isNotBlank(currentUsername)) {
            User currentUser = findUserByUsername(currentUsername);
            if (currentUser == null) {
                throw new UsernameNotFoundException("No user found by username: " + currentUsername);
            }
            User userByNewUsername = findUserByUsername(newUsername);
            if (userByNewUsername != null && currentUser.getId().equals(userByNewUsername.getId())) {
                throw new UsernameExistException("Username already exists");
            }
            User userByNewEmail = findUserByEmail(newEmail);
            if (userByNewEmail != null && currentUser.getId().equals(userByNewEmail.getId())) {
                throw new EmailExistException("Email already exists");
            }
            return currentUser;
        } else {
            User userByUsername = findUserByUsername(newUsername);
            if (userByUsername != null) {
                throw new UsernameExistException("Username already exists");
            }
            User userByEmail = findUserByEmail(newEmail);
            if (userByEmail != null) {
                throw new EmailExistException("Email already exists");
            }
            return null;
        }
    }

    @Override
    public List<User> getUsers() {
        return null;
    }

    @Override
    public User findUserByUsername(String username) {
        return null;
    }

    @Override
    public User findUserByEmail(String email) {
        return null;
    }
}
