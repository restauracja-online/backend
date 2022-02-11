package pl.wsiz.foodservice.controller.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class IngredientRequest {
    @JsonProperty("ing_id")
    private Long id;
    @JsonProperty("ing_name")
    private String name;
    @JsonProperty("ing_price")
    private double price;
}
