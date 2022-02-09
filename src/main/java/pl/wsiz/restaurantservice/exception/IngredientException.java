package pl.wsiz.foodservice.exception;

public class IngredientException extends RuntimeException {
    public IngredientException(final String name) {
        super(name);
    }

    public IngredientException(String message, Throwable cause) {
        super(message, cause);
    }
}
