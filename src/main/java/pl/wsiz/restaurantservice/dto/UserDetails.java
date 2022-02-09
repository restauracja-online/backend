package pl.wsiz.foodservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.wsiz.foodservice.model.EntityStatus;
import pl.wsiz.foodservice.model.Role;

import java.util.Collection;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDetails {

    private String email;
    private EntityStatus status;
    private Role role;
    private Collection<AddressDetails> addresses;
}
