package pl.wsiz.foodservice.exception;

public class CartException extends RuntimeException {

    public CartException(final String name) {
        super(name);
    }

    public CartException(String message, Throwable cause) {
        super(message, cause);
    }

}
