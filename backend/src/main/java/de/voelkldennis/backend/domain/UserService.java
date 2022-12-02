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

import static de.voelkldennis.backend.jwt.constant.UserImplConstant.*;
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
            LOGGER.error(NO_USER_FOUND_BY_USERNAME + username);
            throw new UsernameNotFoundException(NO_USER_FOUND_BY_USERNAME + username);
        } else {
            user.setLastLoginDateDisplay(user.getLastLoginDate());
            user.setLastLoginDate(new Date());
            userRepo.save(user);
            UserService userService = new UserService();
            LOGGER.info(RETURNING_FOUND_USER_BY_USERNAME + username);
            return userService;
        }
    }

    public User register(NewUserDTO newUserDTO)
            throws EmailExistException,
            UsernameExistException,
            UsernameNotFoundException {

        validateNewUsernameAndEmail(
                StringUtils.EMPTY,
                newUserDTO.username(),
                newUserDTO.email());

        User appUser = new User();
        appUser.setId(generateUserId());
        appUser.setUserId(generateUserId());
        String password = generatePassword();
        String encodedPassword = encodePassword(password);
        appUser.setFirstName(newUserDTO.firstName());
        appUser.setLastName(newUserDTO.lastName());
        appUser.setUsername(newUserDTO.username());
        appUser.setEmail(newUserDTO.email());
        appUser.setJoinDate(new Date());
        appUser.setPassword(encodedPassword);
        appUser.setActive(true);
        appUser.setNotLocked(true);
        appUser.setRole(Role.ROLE_USER.name());
        appUser.setAuthorities(Role.ROLE_USER.getAuthorities());
        appUser.setProfileImageUrl(getTemporaryProfileImageUrl(newUserDTO.username()));
        userRepo.save(appUser);
        LOGGER.info("New user password (LOG only for developing purpose): " + password);

        return appUser;
    }

    private String getTemporaryProfileImageUrl(String username) {
        // for later configuration of the profile image
        return ServletUriComponentsBuilder.fromCurrentContextPath().path(USER_IMAGE_PROFILE_TEMP).toUriString();
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

        User userByNewUsername = findUserByUsername(newUsername);
        User userByNewEmail = findUserByEmail(newEmail);

        if (StringUtils.isNotBlank(currentUsername)) {
            User currentUser = findUserByUsername(currentUsername);
            if (currentUser == null) {
                throw new UsernameNotFoundException(NO_USER_FOUND_BY_USERNAME + currentUsername);
            }
            if (userByNewUsername != null && currentUser.getId().equals(userByNewUsername.getId())) {
                throw new UsernameExistException(USERNAME_ALREADY_EXISTS);
            }
            if (userByNewEmail != null && currentUser.getId().equals(userByNewEmail.getId())) {
                throw new EmailExistException(EMAIL_ALREADY_EXISTS);
            }
            return currentUser;
        } else {
            if (userByNewUsername != null) {
                throw new UsernameExistException(USERNAME_ALREADY_EXISTS);
            }
            if (userByNewEmail != null) {
                throw new EmailExistException(EMAIL_ALREADY_EXISTS);
            }
            return null;
        }
    }

    public List<User> getUsers() {
        return userRepo.findAll();
    }

    public User findUserByUsername(String username) {
        return userRepo.findByUsername(username);
    }

    public User findUserByEmail(String email) {
        return userRepo.findUserByEmail(email);
    }
}
