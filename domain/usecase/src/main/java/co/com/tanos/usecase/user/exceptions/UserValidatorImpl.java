package co.com.tanos.usecase.user.exceptions;

import co.com.tanos.model.user.User;
import co.com.tanos.model.user.exceptions.ValidationException;
import co.com.tanos.model.user.gateways.UserRepository;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.util.regex.Pattern;

public class UserValidatorImpl implements UserValidator {

    public final UserRepository userRepository;

    private final Pattern EMAIL_VALIDATOR = Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");
    private final Pattern NUMBER_VALIDATOR = Pattern.compile("^[0-9]+$");

    public UserValidatorImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Mono<User> validateRequiredFields(User user) {
        return Mono.just(user)
                .flatMap(usr -> usr.getName() == null || usr.getName().isBlank()
                        ? Mono.error(new ValidationException("El nombre es obligatorio"))
                        : Mono.just(usr))
                .flatMap(usr -> usr.getLastName() == null || usr.getLastName().isBlank()
                        ? Mono.error(new ValidationException("El apellido es obligatorio"))
                        : Mono.just(usr))
                .flatMap(usr -> usr.getEmail() == null || usr.getEmail().isBlank()
                        ? Mono.error(new ValidationException("El email es obligatorio"))
                        : Mono.just(usr))
                .flatMap(usr -> usr.getSalary() == null || usr.getSalary() < 0 || usr.getSalary() > 15000000
                        ? Mono.error(new ValidationException("Salario inválido"))
                        : Mono.just(usr))
                .flatMap(usr -> usr.getBirdDate() == null || usr.getBirdDate().isAfter(LocalDate.now())
                        ? Mono.error(new ValidationException("Fecha de nacimiento inválida"))
                        : Mono.just(usr))
                .flatMap(usr -> usr.getAddress() == null || usr.getAddress().isBlank()
                        ? Mono.error(new ValidationException("Dirección es obligatoria"))
                        : Mono.just(usr))
                .flatMap(usr -> usr.getPhoneNumber() == null || usr.getPhoneNumber().isBlank()
                        ? Mono.error(new ValidationException("Teléfono es obligatorio"))
                        : Mono.just(usr))
                .flatMap(usr -> usr.getDni() == null || user.getDni().toString().isBlank()
                        ? Mono.error(new ValidationException("dni es obligatorio"))
                        : Mono.just(usr));
    }

    @Override
    public Mono<User> validateFormateEmail(User user) {
        return EMAIL_VALIDATOR.matcher(user.getEmail()).matches()
                ? Mono.just(user) : Mono.error(new ValidationException("Formato de email inválido"));
    }

    @Override
    public Mono<User> validateSingleEmail(User user) {
        return userRepository.findByEmail(user.getEmail())
                .flatMap(existing -> Mono.error(new ValidationException("Email ya registrado")))
                .switchIfEmpty(Mono.just(user)).cast(User.class);
    }

    @Override
    public Mono<User> validateSingleDni(User user) {
        return userRepository.findByDni(user.getDni())
                .flatMap(existing -> Mono.error(new ValidationException("Dni ya registrado")))
                .switchIfEmpty(Mono.just(user)).cast(User.class);
    }

    @Override
    public Mono<User> validateEmptyDni(Long dni) {
        return userRepository.findByDni(dni)
                .flatMap(Mono::just)
                .switchIfEmpty(Mono.error(new ValidationException("El DNI no existe")));

    }

}

