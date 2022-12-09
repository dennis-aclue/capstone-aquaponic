package de.voelkldennis.backend.domain;

import de.voelkldennis.backend.exception.domain.EmailExistException;
import de.voelkldennis.backend.exception.domain.UserNotFoundException;
import de.voelkldennis.backend.exception.domain.UsernameExistException;

import java.io.IOException;

public interface UserService {

    User register(UserDTO userDTO) throws UserNotFoundException, UsernameExistException, EmailExistException;

    User findUserByUsername(String username);

    User findUserByEmail(String email);

    void deleteUser(String username) throws IOException;

    User updateUser(String userId, UserDTO userDTO) throws UserNotFoundException, EmailExistException, UsernameExistException;

    boolean isIdExisting(String userId);
}
