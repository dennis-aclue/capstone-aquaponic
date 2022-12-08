package de.voelkldennis.backend.domain;

import de.voelkldennis.backend.ProjectUtils;
import de.voelkldennis.backend.domain.enumeration.Role;
import de.voelkldennis.backend.exception.domain.EmailExistException;
import de.voelkldennis.backend.exception.domain.UserNotFoundException;
import de.voelkldennis.backend.exception.domain.UsernameExistException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;

import static de.voelkldennis.backend.domain.enumeration.Role.ROLE_USER;
import static de.voelkldennis.backend.jwt.constant.FileConstant.*;
import static de.voelkldennis.backend.jwt.constant.UserImplConstant.*;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;
import static org.apache.commons.lang3.StringUtils.EMPTY;

@Service
@Transactional
@Qualifier("userDetailsService")
public class UserServiceImpl implements UserService, UserDetailsService {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final ProjectUtils projectUtils;

    private void saveProfileImage(User user, MultipartFile profileImage) throws IOException {
        if (profileImage != null) {
            Path userFolder = Paths.get(USER_FOLDER + user.getUsername()).toAbsolutePath().normalize();
            if (Files.exists(userFolder)) {
                Files.createDirectories(userFolder);
                logger.info(DIRECTORY_CREATED);
            }
            Files.deleteIfExists(Paths.get(userFolder + user.getUsername() + DOT + JPG_EXTENSION));
            Files.copy(profileImage.getInputStream(), userFolder.resolve(user.getUsername() + DOT + JPG_EXTENSION), REPLACE_EXISTING);
            user.setProfileImageUrl(setProfileImageUrl(user.getUsername()));
            userRepository.save(user);
        }
    }

    private String setProfileImageUrl(String username) {
        return ServletUriComponentsBuilder.fromCurrentContextPath().path(USER_IMAGE_PATH + username + FORWARD_SLASH
                + username + DOT + JPG_EXTENSION).toUriString();
    }

    private Role getRoleEnumName(String role) {
        return Role.valueOf(role.toUpperCase());
    }

    private String getTemporaryProfileImageUrl(String username) {
        return ServletUriComponentsBuilder.fromCurrentContextPath().path(DEFAULT_USER_IMAGE_PATH + username).toUriString();
    }

    private String encodePassword(String password) {
        return passwordEncoder.encode(password);
    }

    private String generatePassword() {
        return projectUtils.generateUUID();
    }

    private String generateUserId() {
        return projectUtils.generateUUID();
    }

    private User validateNewUsernameAndEmail(String currentUsername, String newUsername, String newEmail)
            throws UserNotFoundException, UsernameExistException,
            EmailExistException {
        User currentUser = new User();
        User userByNewUsername = findUserByUsername(newUsername);
        User userByNewEmail = findUserByEmail(newEmail);
        if (StringUtils.isNotBlank(currentUsername)) {
            currentUser = findUserByUsername(currentUsername);
            if (currentUser == null) {
                throw new UserNotFoundException(NO_USER_FOUND_BY_USERNAME + currentUsername);
            }
            if (userByNewUsername != null && !currentUser.getId().equals(userByNewUsername.getId())) {
                throw new UsernameExistException(USERNAME_ALREADY_EXISTS);
            }
            if (userByNewEmail != null && !currentUser.getId().equals(userByNewEmail.getId())) {
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
            return currentUser;
        }
    }

    @Autowired
    public UserServiceImpl(
            UserRepository userRepository,
            BCryptPasswordEncoder passwordEncoder,
            ProjectUtils projectUtils) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.projectUtils = projectUtils;
    }

    @Override
    public User addNewUser(UserDTO userDTO) throws
            UserNotFoundException,
            EmailExistException,
            UsernameExistException {
        validateNewUsernameAndEmail(EMPTY, userDTO.username(), userDTO.password());
        User newUser = new User();
        newUser.setUserId(generateUserId());
        String password = generatePassword();
        String encodedPassword = encodePassword(password);
        newUser.setPassword(encodedPassword);
        newUser.setFirstName(userDTO.firstName());
        newUser.setLastName(userDTO.lastName());
        newUser.setUsername(userDTO.username());
        newUser.setEmail(userDTO.email());
        newUser.setJoinDate(new Date());
        newUser.setActive(true);
        newUser.setNotLocked(false);
        newUser.setRole(ROLE_USER.name());
        newUser.setAuthorities(ROLE_USER.getAuthorities());
        newUser.setProfileImageUrl(getTemporaryProfileImageUrl(newUser.getUsername()));
        userRepository.save(newUser);
        return newUser;
    }

    @Override
    public User updateUser(String currentUsername, String newFirstName, String newLastName, String newUsername, String newEmail, String role, boolean isNonLocked, boolean isActive, MultipartFile profileImage) throws UserNotFoundException, UsernameExistException, EmailExistException, IOException {
        User currentUser = validateNewUsernameAndEmail(currentUsername, newUsername, newEmail);
        currentUser.setFirstName(newFirstName);
        currentUser.setLastName(newLastName);
        currentUser.setUsername(newUsername);
        currentUser.setEmail(newEmail);
        currentUser.setActive(isActive);
        currentUser.setNotLocked(isNonLocked);
        currentUser.setRole(getRoleEnumName(role).name());
        currentUser.setAuthorities(getRoleEnumName(role).getAuthorities());
        userRepository.save(currentUser);
        saveProfileImage(currentUser, profileImage);
        return currentUser;
    }

    @Override
    public void deleteUser(String id) {
        userRepository.deleteById(id);
    }

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findUserByUsername(username);
        if (user == null) {
            logger.error(String.format(NO_USER_FOUND_BY_USERNAME, username));
            throw new UsernameNotFoundException(String.format(NO_USER_FOUND_BY_USERNAME, username));
        } else {
            user.setLastLoginDateDisplay(user.getLastLoginDate());
            user.setLastLoginDate(new Date());
            userRepository.save(user);
            UserPrincipal userPrincipal = new UserPrincipal(user);
            logger.info(String.format(LOGIN_SUCCESSFUL, username));
            return userPrincipal;
        }
    }

    @Override
    public User register(UserDTO userDTO)
            throws
            UserNotFoundException,
            UsernameExistException,
            EmailExistException {
        validateNewUsernameAndEmail(EMPTY, userDTO.username(), userDTO.email());
        User user = new User();
        user.setUsername(userDTO.username());
        user.setFirstName(userDTO.firstName());
        user.setLastName(userDTO.lastName());
        user.setEmail(userDTO.email());
        user.setUserId(generateUserId());
        String password = generatePassword();
        user.setJoinDate(new Date());
        user.setPassword(encodePassword(password));
        user.setActive(true);
        user.setNotLocked(true);
        user.setRole(ROLE_USER.name());
        user.setAuthorities(ROLE_USER.getAuthorities());
        user.setProfileImageUrl(getTemporaryProfileImageUrl(userDTO.username()));
        logger.info(String.format(NEW_USER_PASSWORD, password));
        return userRepository.save(user);
    }

    @Override
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    @Override
    public User findUserByUsername(String username) {
        return userRepository.findUserByUsername(username);
    }

    @Override
    public User findUserByEmail(String email) {
        return userRepository.findUserByEmail(email);
    }


}