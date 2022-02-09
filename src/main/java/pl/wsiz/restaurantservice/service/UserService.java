package pl.wsiz.foodservice.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.wsiz.foodservice.controller.model.AddressRequest;
import pl.wsiz.foodservice.exception.UserException;
import pl.wsiz.foodservice.exception.UserExistsException;
import pl.wsiz.foodservice.model.Address;
import pl.wsiz.foodservice.model.EntityStatus;
import pl.wsiz.foodservice.model.Role;
import pl.wsiz.foodservice.model.User;
import pl.wsiz.foodservice.repository.AddressRepository;
import pl.wsiz.foodservice.repository.UserRepository;

import java.util.Optional;

@Slf4j
@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AddressRepository addressRepository;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, AddressRepository addressRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.addressRepository = addressRepository;
    }

    public User getUserById(Long id) {
        return userRepository
                .findById(id)
                .orElseGet(() -> {
                    log.info("User with id {} not found", id);
                    return null;
                });
    }

    @Secured({"ROLE_ADMIN", "ROLE_USER"})
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseGet(() -> {
                    log.info("User with email {} not found", email);
                    return null;
                });
    }

    public User save(User user) {
        return userRepository.save(user);
    }

    public User createUser(User user) {
        validateUserExists(user.getEmail());
        user.setRole(Role.ROLE_USER);
        user.setStatus(EntityStatus.ACTIVE);
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        return save(user);
    }

    public User addAddress(final String userName, final AddressRequest addressRequest) {
        validateUserAddress(addressRequest);

        Optional<User> user = this.userRepository.findByEmail(userName);

        if (!user.isPresent()) {
            throw new UserException(String.format("Can't find user %s", userName));
        }

        Optional<Address> address = this.addressRepository.findByUserId(user.get().getId());

        if (address.isPresent()) {
            Address addressToSave = address.get();
            addressToSave.setCity(addressRequest.getCity());
            addressToSave.setZipCode(addressRequest.getZip_code());
            addressToSave.setBuildingNumber(addressRequest.getBuilding_number());
            addressToSave.setStreet(addressRequest.getStreet());

            this.addressRepository.save(addressToSave);

            return user.get();
        }

        Address addressToSave = new Address();
        addressToSave.setCity(addressRequest.getCity());
        addressToSave.setZipCode(addressRequest.getZip_code());
        addressToSave.setBuildingNumber(addressRequest.getBuilding_number());
        addressToSave.setStreet(addressRequest.getStreet());
        addressToSave.setUser(user.get());
        addressToSave.setStatus(EntityStatus.ACTIVE);

        this.addressRepository.save(addressToSave);

        return user.get();
    }

    private void validateUserExists(String email) {
        Optional<User> candidate = userRepository.findByEmail(email);
        if (candidate.isPresent()) {
            throw new UserExistsException(String.format("User with given email %s already exists.", email));
        }
    }

    private void validateUserAddress(final AddressRequest addressRequest) {
        String city = Optional.ofNullable(addressRequest.getCity()).orElse("");
        String zipCode = Optional.ofNullable(addressRequest.getZip_code()).orElse("");
        String street = Optional.ofNullable(addressRequest.getStreet()).orElse("");
        String buldingNumber = Optional.ofNullable(addressRequest.getBuilding_number()).orElse("");

        if (city.isEmpty() || zipCode.isEmpty() || street.isEmpty() || buldingNumber.isEmpty()) {
            throw new UserException("Can't save address");
        }
    }
}
