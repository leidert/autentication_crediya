package co.com.tanos.model.user.auth.exceptions;

import co.com.tanos.model.user.exceptions.ValidationException;

public class TokenExpiredException  extends ValidationException {
    public TokenExpiredException() {
        super("Token has expired");
    }
}
