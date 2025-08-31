package co.com.tanos.usecase.user.exceptions;

import co.com.tanos.model.user.User;
import reactor.core.publisher.Mono;

public interface UserValidator {

    public Mono<User> validateRequiredFields(User user);
    public Mono<User> validateFormateEmail(User user);
    public Mono<User> validateSingleEmail(User user);
    public Mono<User> validateSingleDni(User user);
    public Mono<User> validateEmptyDni(Long dni);

}
