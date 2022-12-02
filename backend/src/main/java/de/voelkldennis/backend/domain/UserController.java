package de.voelkldennis.backend.domain;

import de.voelkldennis.backend.exception.domain.EmailExistException;
import de.voelkldennis.backend.exception.domain.ExceptionHandling;
import de.voelkldennis.backend.exception.domain.UsernameExistException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = {"/", "/user"})
public class UserController extends ExceptionHandling {

    public UserService userService;

    @Autowired
    public void UserResource(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody NewUserDTO newUserDTO) throws
            EmailExistException,
            UsernameExistException {
        return new ResponseEntity<>(userService.register(newUserDTO), HttpStatus.OK);
    }

}
