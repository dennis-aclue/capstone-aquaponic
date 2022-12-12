package de.voelkldennis.backend.domain;

import de.voelkldennis.backend.ProjectUtils;
import de.voelkldennis.backend.exception.domain.EmailExistException;
import de.voelkldennis.backend.exception.domain.UsernameExistException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.mail.MessagingException;
import java.util.Date;

import static de.voelkldennis.backend.domain.Role.ROLE_SUPER_ADMIN;
import static de.voelkldennis.backend.jwt.constant.FileConstant.DEFAULT_USER_IMAGE_PATH;
import static de.voelkldennis.backend.jwt.constant.UserImplConstant.*;

@Service
@Transactional
@Qualifier("userDetailsService")
public class UserServiceImpl implements UserService, UserDetailsService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final ProjectUtils projectUtils;
    private final EmailService emailService;

    @Autowired
    public UserServiceImpl(
            UserRepository userRepository,
            BCryptPasswordEncoder passwordEncoder,
            ProjectUtils projectUtils) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.projectUtils = projectUtils;
        this.emailService = new EmailService();
    }

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

    private void validateNewUsernameAndEmail(String newUsername, String newEmail)
            throws UsernameExistException,
            EmailExistException {
        User userByNewUsername = findUserByUsername(newUsername);
        User userByNewEmail = findUserByEmail(newEmail);
        if (userByNewUsername != null) {
            throw new UsernameExistException(USERNAME_ALREADY_EXISTS);
        }
        if (userByNewEmail != null) {
            throw new EmailExistException(EMAIL_ALREADY_EXISTS);
        }
    }

    @Override
    public User updateUser(UserDTO updateUser) {
        User toUpdateUser = findUserByUsername(updateUser.username());
        toUpdateUser.setFirstName(updateUser.firstName());
        toUpdateUser.setLastName(updateUser.lastName());
        toUpdateUser.setUsername(updateUser.username());
        toUpdateUser.setEmail(updateUser.email());

        return userRepository.save(toUpdateUser);
    }

    @Override
    public void deleteUser(String id) {
        userRepository.deleteById(id);
    }

    //@Override
    public void resetPassword(String email) throws MessagingException {
        User user = findUserByEmail(email);
        String password = generatePassword();
        String encodedPassword = encodePassword(password);
        user.setPassword(encodedPassword);
        emailService.sendNewPasswordEmail(user.getFirstName(), password, user.getEmail());
        userRepository.save(user);
    }

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findUserByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException(String.format(NO_USER_FOUND_BY_USERNAME, username));
        } else {
            user.setLastLoginDateDisplay(user.getLastLoginDate());
            user.setLastLoginDate(new Date());
            userRepository.save(user);
            return new UserPrincipal(user);
        }
    }

    @Override
    public User register(UserDTO userDTO)
            throws
            UsernameExistException,
            EmailExistException, MessagingException {
        validateNewUsernameAndEmail(userDTO.username(), userDTO.email());
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
        emailService.sendNewPasswordEmail(userDTO.firstName(), password, userDTO.email());
        return userRepository.save(user);
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