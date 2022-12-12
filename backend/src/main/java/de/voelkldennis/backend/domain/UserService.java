package de.voelkldennis.backend.domain;

import de.voelkldennis.backend.exception.domain.EmailExistException;
import de.voelkldennis.backend.exception.domain.UserNotFoundException;
import de.voelkldennis.backend.exception.domain.UsernameExistException;

import javax.mail.MessagingException;
import java.io.IOException;

public interface UserService {

    User register(UserDTO userDTO) throws UserNotFoundException, UsernameExistException, EmailExistException, MessagingException;

    User findUserByUsername(String username);

    User findUserByEmail(String email);

    void deleteUser(String username) throws IOException;

    User updateUser(UserDTO updateUser) throws UserNotFoundException, EmailExistException, UsernameExistException;

    void resetPassword(String email) throws MessagingException;

}
