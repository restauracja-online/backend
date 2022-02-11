package pl.wsiz.foodservice.controller.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class DishRequest {
    @JsonProperty("dish_id")
    private Long id;
    @JsonProperty("dish_name")
    private String name;
    @JsonProperty("dish_desc")
    private String description;
    @JsonProperty("dish_price")
    private Double price;
    @JsonProperty("dish_img")
    private String imgURL;
    @JsonProperty("dish_ing")
    List<IngredientRequest> ingredients;
}
