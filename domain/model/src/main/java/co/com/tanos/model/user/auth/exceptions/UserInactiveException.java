package co.com.tanos.model.user.auth.exceptions;

import co.com.tanos.model.user.exceptions.ValidationException;

public class UserInactiveException extends ValidationException {
    public UserInactiveException() {
        super("User account is inactive");
    }
}
