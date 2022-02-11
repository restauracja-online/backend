package pl.wsiz.foodservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.wsiz.foodservice.model.EntityStatus;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddressDetails {

    private String city;
    private String zipCode;
    private String street;
    private String buildingNumber;
    private EntityStatus status;
}
