package de.voelkldennis.backend.domain;

import de.voelkldennis.backend.ProjectUtils;
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
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.Date;
import java.util.List;

import static de.voelkldennis.backend.domain.enumeration.Role.ROLE_SUPER_ADMIN;
import static de.voelkldennis.backend.jwt.constant.FileConstant.DEFAULT_USER_IMAGE_PATH;
import static de.voelkldennis.backend.jwt.constant.UserImplConstant.*;
import static org.apache.commons.lang3.StringUtils.EMPTY;

@Service
@Transactional
@Qualifier("userDetailsService")
public class UserServiceImpl implements UserService, UserDetailsService {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final ProjectUtils projectUtils;

    private String getTemporaryProfileImageUrl(String username) {
        return ServletUriComponentsBuilder.fromCurrentContextPath().path(DEFAULT_USER_IMAGE_PATH + username).toUriString();
    }

    private String encodePassword(String password) {
        return passwordEncoder.encode(password);
    }

    private String generatePassword() {
        String randomUUID = projectUtils.generateUUID();
        return randomUUID.substring(0, 8);
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
    public User updateUser(String userId, UserDTO userDTO) {
        User updateUser = new User();
        updateUser.setFirstName(userDTO.firstName());
        updateUser.setLastName(userDTO.lastName());
        updateUser.setUsername(userDTO.username());
        updateUser.setEmail(userDTO.email());
        return userRepository.save(updateUser);
    }

    @Override
    public boolean isIdExisting(String userId) {
        List<User> user = userRepository.findAll();
        for (User u : user) {
            if (u.getId().equals(userId)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void deleteUser(String id) {
        userRepository.deleteById(id);
    }

    //@Override
    public void resetPassword(String email) {
        User user = findUserByEmail(email);
        String password = generatePassword();
        String encodedPassword = encodePassword(password);
        user.setPassword(encodedPassword);
        userRepository.save(user);
    }

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findUserByUsername(username);
        if (user == null) {
            //logger.error(String.format(NO_USER_FOUND_BY_USERNAME, username));
            throw new UsernameNotFoundException(String.format(NO_USER_FOUND_BY_USERNAME, username));
        } else {
            user.setLastLoginDateDisplay(user.getLastLoginDate());
            user.setLastLoginDate(new Date());
            userRepository.save(user);
            //logger.info(String.format(LOGIN_SUCCESSFUL, username));
            return new UserPrincipal(user);
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
        user.setRole(ROLE_SUPER_ADMIN.name());
        user.setAuthorities(ROLE_SUPER_ADMIN.getAuthorities());
        user.setProfileImageUrl(getTemporaryProfileImageUrl(userDTO.username()));
        logger.info(String.format(NEW_USER_PASSWORD, password));

        return userRepository.save(user);
    }

    //@Override
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