package pl.wsiz.restaurantservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.wsiz.restaurantservice.model.Role;

import java.util.Collection;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDetails {

    private String email;
    private Role role;
    private Collection<AddressDetails> addresses;
}
