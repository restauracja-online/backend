package pl.wsiz.foodservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderRowDto {
    private Long orderRowId;
    private Long dishId;
    private DishDto dishDto;
    private int dishQuantity;
}
