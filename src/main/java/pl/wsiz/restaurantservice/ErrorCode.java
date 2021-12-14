package pl.wsiz.restaurantservice;

public enum ErrorCode {

    MISSING_ERROR_CODE("error.missing-error-code"),
    UNAUTHORIZED("error.authorization.unauthorized"),
    FORBIDDEN("error.authentication.forbidden"),
    USER_EXISTS("error.validation.user-exists");

    private final String code;

    ErrorCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
