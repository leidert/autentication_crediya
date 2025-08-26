package co.com.tanos.model.user;

public class ValidationException extends RuntimeException{

    public ValidationException(String message) {
        super(message);
    }
}
