package de.voelkldennis.backend.domain;

import de.voelkldennis.backend.exception.domain.ExceptionHandling;
import de.voelkldennis.backend.exception.domain.UserNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = {"/", "/user"})
public class UserController extends ExceptionHandling {

    public UserService userService;

    @GetMapping("/home")
    public String showUser() throws UserNotFoundException {
        //return "test";
        throw new UserNotFoundException("This user does not exist");
    }

}
