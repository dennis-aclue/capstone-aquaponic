package de.voelkldennis.backend.domain;

import de.voelkldennis.backend.exception.domain.EmailExistException;
import de.voelkldennis.backend.exception.domain.UserNotFoundException;
import de.voelkldennis.backend.exception.domain.UsernameExistException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface UserService {

    User register(UserDTO userDTO) throws UserNotFoundException, UsernameExistException, EmailExistException;

    List<User> getUsers();

    User findUserByUsername(String username);

    User findUserByEmail(String email);

    User addNewUser(UserDTO userDTO) throws UserNotFoundException, EmailExistException, UsernameExistException;

    User updateUser(String currentUsername, String currentFirstName, String newLastName, String newUsername, String newEmail, String role, boolean isNonLocked, boolean isActive, MultipartFile profileImage) throws UserNotFoundException, UsernameExistException, EmailExistException, IOException;

    void deleteUser(String username) throws IOException;

}
