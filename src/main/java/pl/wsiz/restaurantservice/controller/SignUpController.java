package pl.wsiz.restaurantservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.wsiz.restaurantservice.dto.Credentials;
import pl.wsiz.restaurantservice.dto.UserDetails;
import pl.wsiz.restaurantservice.model.User;
import pl.wsiz.restaurantservice.service.UserService;

import javax.validation.Valid;

import static pl.wsiz.restaurantservice.dto.Converter.signUpToUser;
import static pl.wsiz.restaurantservice.dto.Converter.userToUserDetails;

@RestController
@RequestMapping(value = "/api", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
public class SignUpController {

    private final UserService userService;

    @Autowired
    public SignUpController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/sign-up")
    public ResponseEntity<UserDetails> singUp(@RequestBody @Valid Credentials signup) {
        User user = userService.createUser(signUpToUser(signup));
        return new ResponseEntity<>(userToUserDetails(user), HttpStatus.CREATED);
    }
}
