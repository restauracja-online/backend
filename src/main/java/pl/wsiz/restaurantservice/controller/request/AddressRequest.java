package pl.wsiz.restaurantservice.controller.request;

import lombok.Data;

@Data
public class AddressRequest {

    private Long id;
    private String city;
    private String zip_code;
    private String street;
    private String building_number;
    private String user_email;
}
