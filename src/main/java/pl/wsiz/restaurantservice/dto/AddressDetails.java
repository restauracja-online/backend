package pl.wsiz.restaurantservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddressDetails {


    private String city;
    private String zipCode;
    private String street;
    private String buildingNumber;
}
