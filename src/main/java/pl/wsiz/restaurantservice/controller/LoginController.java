package pl.wsiz.foodservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.wsiz.foodservice.dto.Credentials;
import pl.wsiz.foodservice.dto.TokenResponse;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

@RestController
@RequestMapping(value = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
public class LoginController {

    private static final String TOKEN_PREFIX = "Basic ";

    private final AuthenticationManager authManager;

    @Autowired
    public LoginController(AuthenticationManager authManager) {
        this.authManager = authManager;
    }

    /**
     * We are aware that this solution is extremely unsafe and shouldn't be use in production application.
     * It's only prototype and ultimately should be replaced by authentication based on JWT token or OAuth.
     */
    @PostMapping("/login")
    public ResponseEntity<TokenResponse> login(@RequestBody Credentials credentials) {
        UsernamePasswordAuthenticationToken authRequest
                = new UsernamePasswordAuthenticationToken(credentials.getEmail(), credentials.getPassword());
        Authentication authentication = authManager.authenticate(authRequest);
        TokenResponse tokenResponse = new TokenResponse(createToken(credentials));

        return new ResponseEntity<>(tokenResponse, HttpStatus.OK);
    }

    private String createToken(Credentials credentials) {
        return TOKEN_PREFIX + Base64.getEncoder().encodeToString(getCredentialsAsByteArray(credentials));
    }

    private byte[] getCredentialsAsByteArray(Credentials credentials) {
        return (credentials.getEmail() + ":" + credentials.getPassword()).getBytes(StandardCharsets.UTF_8);
    }
}
