package de.voelkldennis.backend.domain.enumeration;

import de.voelkldennis.backend.domain.User;
import de.voelkldennis.backend.exception.domain.EmailExistException;
import de.voelkldennis.backend.exception.domain.UsernameExistException;

import java.util.List;

public interface UserServiceImpl {

    User register(String firstName, String lastName, String username, String email) throws EmailExistException, UsernameExistException;

    List<User> getUsers();

    User findUserByUsername(String username);

    User findUserByEmail(String email);

}
