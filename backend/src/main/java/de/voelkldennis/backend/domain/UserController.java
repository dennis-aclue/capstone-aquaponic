package de.voelkldennis.backend.domain;

import de.voelkldennis.backend.exception.domain.EmailExistException;
import de.voelkldennis.backend.exception.domain.ExceptionHandling;
import de.voelkldennis.backend.exception.domain.UserNotFoundException;
import de.voelkldennis.backend.exception.domain.UsernameExistException;
import de.voelkldennis.backend.jwt.utility.HttpResponse;
import de.voelkldennis.backend.jwt.utility.JWTTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.io.IOException;

import static de.voelkldennis.backend.jwt.constant.SecurityConstant.JWT_TOKEN_HEADER;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping(path = {"/", "/user"})
public class UserController extends ExceptionHandling {
    public static final String USER_DELETED_SUCCESSFULLY = "User deleted successfully";
    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final UserRepository userRepository;
    private final JWTTokenProvider jwtTokenProvider;

    @Autowired
    public UserController(AuthenticationManager authenticationManager, UserService userService, UserRepository userRepository, JWTTokenProvider jwtTokenProvider) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.userRepository = userRepository;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    private ResponseEntity<HttpResponse> response() {
        return new ResponseEntity<>(new HttpResponse(HttpStatus.OK.value(), HttpStatus.OK, HttpStatus.OK.getReasonPhrase().toUpperCase(),
                UserController.USER_DELETED_SUCCESSFULLY), HttpStatus.OK);
    }

    private HttpHeaders getJwtHeader(UserPrincipal user) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(JWT_TOKEN_HEADER, jwtTokenProvider.generateJwtToken(user));
        return headers;
    }

    private void authenticate(String username, String password) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
    }

    @PostMapping("/login")
    public ResponseEntity<User> login(@RequestBody UserDTO userDTO) {
        authenticate(userDTO.username(), userDTO.password());
        User loginUser = userService.findUserByUsername(userDTO.username());
        UserPrincipal userPrincipal = new UserPrincipal(loginUser);
        HttpHeaders jwtHeader = getJwtHeader(userPrincipal);
        return new ResponseEntity<>(loginUser, jwtHeader, OK);
    }

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody @Valid UserDTO userDTO) throws
            UserNotFoundException,
            EmailExistException,
            UsernameExistException {
        User newUserDTO = userService.register(userDTO);
        return new ResponseEntity<>(newUserDTO, OK);
    }

    @PutMapping("/updateUser/{dbUserId}")
    public User updateUser(@PathVariable String dbUserId, @RequestBody UserDTO userDTO) throws UserNotFoundException, EmailExistException, UsernameExistException {
        if (userRepository.findById(dbUserId).isPresent()) {
            return userService.updateUser(dbUserId, userDTO);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }
    }

    @DeleteMapping("/delete/{dbUserId}")
    //@PreAuthorize("hasAnyAuthority('user:delete')")
    public ResponseEntity<HttpResponse> deleteUser(@PathVariable String dbUserId) throws IOException {
        userService.deleteUser(dbUserId);
        return response();
    }

    @PostMapping("/resetPassword/{email}")
    public void resetPassword(@PathVariable String email) {
        userService.resetPassword(email);
    }

    @GetMapping("/getUserData/{id}")
    public ResponseEntity<User> getUserData(@PathVariable String id) {
        String username = userRepository.findById(id).get().getUsername();
        return new ResponseEntity<>(userService.findUserByUsername(username), OK);
    }

}
