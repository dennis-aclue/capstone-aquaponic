package de.voelkldennis.backend.domain;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    public UserService userService;

    @GetMapping("/home")
    public String showUser() {
        return "test";
    }

}
