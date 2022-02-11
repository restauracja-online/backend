package pl.wsiz.foodservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DishDto {
    private Long id;
    private String name;
    private String description;
    private Double price;
    private String imgURL;
    private List<IngredientDto> ingredients;
}
