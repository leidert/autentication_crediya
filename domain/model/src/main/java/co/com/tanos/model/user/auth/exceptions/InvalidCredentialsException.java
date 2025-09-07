package co.com.tanos.model.user.auth.exceptions;

import co.com.tanos.model.user.exceptions.ValidationException;

public class InvalidCredentialsException extends ValidationException {

    public InvalidCredentialsException() {
        super("Invalid username or password");
    }
}
