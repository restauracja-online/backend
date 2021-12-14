package pl.wsiz.restaurantservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.wsiz.restaurantservice.controller.request.AddressRequest;
import pl.wsiz.restaurantservice.dto.Converter;
import pl.wsiz.restaurantservice.dto.UserDetails;
import pl.wsiz.restaurantservice.model.User;
import pl.wsiz.restaurantservice.service.UserService;

import javax.validation.Valid;
import java.security.Principal;

@RestController
@RequestMapping(path = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/user/me")
    public ResponseEntity<UserDetails> getUserDetails(Principal principal) {
        User user = userService.getUserByEmail(principal.getName());

        return new ResponseEntity<>(Converter.userToUserDetails(user), HttpStatus.OK);
    }

    @PostMapping("/user/address")
    public ResponseEntity<UserDetails> saveAddress(Principal principal, @RequestBody @Valid AddressRequest addressRequest) {
        User user = userService.addAddress(principal.getName(), addressRequest);
        return new ResponseEntity<>(Converter.userToUserDetails(user), HttpStatus.OK);
    }
}
