package co.com.tanos.model.user.exceptions;

public class ValidationException extends RuntimeException{

    public ValidationException(String message) {
        super(message);
    }
}
