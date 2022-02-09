package pl.wsiz.foodservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import pl.wsiz.foodservice.ErrorCode;
import pl.wsiz.foodservice.dto.error.Error;
import pl.wsiz.foodservice.dto.error.ErrorMessage;
import pl.wsiz.foodservice.service.ErrorCodesService;

import java.util.Collections;

@ControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(value = {UserExistsException.class})
    public ResponseEntity<Error> handleUserExistsException(RuntimeException e) {
        ErrorMessage message = new ErrorMessage(ErrorCodesService.getTranslatedCode(ErrorCode.USER_EXISTS));
        Error responseBody = Error.builder()
                .code(HttpStatus.BAD_REQUEST.value())
                .status(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .messages(Collections.singletonList(message))
                .build();

        return new ResponseEntity<>(responseBody, HttpStatus.BAD_REQUEST);
    }
}
