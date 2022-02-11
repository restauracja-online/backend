package pl.wsiz.foodservice.controller.model;

import lombok.Data;
import pl.wsiz.foodservice.model.EntityStatus;

@Data
public class AddressRequest {
    private Long id;
    private String city;
    private String zip_code;
    private String street;
    private String building_number;
    private String user_email;
    private EntityStatus status;
}
