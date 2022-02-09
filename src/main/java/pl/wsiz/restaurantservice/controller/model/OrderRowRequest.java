package pl.wsiz.foodservice.controller.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class OrderRowRequest {
    @JsonProperty("orderRowId")
    private Long orderRowId;
    @JsonProperty("dishId")
    private Long dishId;
    @JsonProperty("dishQuantity")
    private int dishQuantity;
}
