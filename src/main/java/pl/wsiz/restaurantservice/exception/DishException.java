package pl.wsiz.foodservice.exception;

public class DishException extends RuntimeException{
    public DishException(final String name) {
        super(name);
    }

    public DishException(String message, Throwable cause) {
        super(message, cause);
    }
}
