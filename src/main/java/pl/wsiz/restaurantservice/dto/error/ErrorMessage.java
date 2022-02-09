package pl.wsiz.foodservice.dto.error;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
// TODO: 15.05.2021 Add constructor with ErrocCode enum and refactor all appearances
public class ErrorMessage {

    private String message;
}
