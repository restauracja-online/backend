package pl.wsiz.restaurantservice.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;
import pl.wsiz.restaurantservice.model.User;
import pl.wsiz.restaurantservice.repository.UserRepository;

@Slf4j
@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Secured({"ROLE_ADMIN", "ROLE_USER"})
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseGet(() -> {
                    log.info("User with email {} not found", email);
                    return null;
                });
    }
}
