package pl.wsiz.restaurantservice.dto;

import lombok.NoArgsConstructor;
import pl.wsiz.restaurantservice.model.Address;
import pl.wsiz.restaurantservice.model.User;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor
public class Converter {

    public static User signUpToUser(Credentials credentials) {
        User user = new User();
        user.setEmail(credentials.getEmail());
        user.setPassword(credentials.getPassword());

        return user;
    }

    public static UserDetails userToUserDetails(User user) {
        return UserDetails.builder()
                .email(user.getEmail())
                .role(user.getRole())
                .addresses(addressesToAddressDetailsList(user.getAddresses()))
                .build();
    }

    public static AddressDetails toAddressDetails(Address address) {
        return AddressDetails.builder()
                .city(address.getCity())
                .street(address.getStreet())
                .zipCode(address.getZipCode())
                .buildingNumber(address.getBuildingNumber())
                .build();
    }

    private static List<AddressDetails> addressesToAddressDetailsList(Collection<Address> addressList) {
        if (addressList == null) {
            return Collections.emptyList();
        }
        return addressList
                .stream()
                .map(Converter::toAddressDetails).collect(Collectors.toList());
    }
}
