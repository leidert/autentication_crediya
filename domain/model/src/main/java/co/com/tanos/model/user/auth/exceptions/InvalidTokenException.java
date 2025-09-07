package co.com.tanos.model.user.auth.exceptions;

import co.com.tanos.model.user.exceptions.ValidationException;

public class InvalidTokenException extends ValidationException {

    public InvalidTokenException(String message) {
        super(message);
    }

    public InvalidTokenException(String message, Throwable cause) {
        super(message, cause);
    }
}
