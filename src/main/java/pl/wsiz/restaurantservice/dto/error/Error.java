package pl.wsiz.restaurantservice.dto.error;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Error {

    private int code;
    private String status;
    private Collection<ErrorMessage> messages;
}
