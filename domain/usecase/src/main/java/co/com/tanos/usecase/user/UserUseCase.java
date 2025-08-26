package co.com.tanos.usecase.user;

import co.com.tanos.model.user.User;
import co.com.tanos.model.user.ValidationException;
import co.com.tanos.model.user.gateways.LoggerGateway;
import co.com.tanos.model.user.gateways.UserRepository;
import lombok.RequiredArgsConstructor;


import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.util.List;

import java.util.regex.Pattern;


@RequiredArgsConstructor
public class UserUseCase  {

    public final UserRepository userRepository;
    public final LoggerGateway loggerGateway;

    private final Pattern EMAIL_VALIDATOR = Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");


    public Mono<User> useCaseUserRegister(User user) {
        return Mono.just(user)
                .flatMap(this::validarCamposObligatorios)
                .flatMap(this::validarEmailFormato)
                .flatMap(this::validarEmailUnico)
                .flatMap(userRepository::userRegister);
    }


    public Flux<List<User>> UseCaseGetAllUser() {
        return userRepository.getAllUser();
    }


    public Mono<User> getUserFindById(Long id) {
        return userRepository.getUserFindById(id);
    }

    private Mono<User> validarCamposObligatorios(User user) {
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
                        : Mono.just(usr));
    }

    private Mono<User> validarEmailFormato(User user) {
        return EMAIL_VALIDATOR.matcher(user.getEmail()).matches()
                ? Mono.just(user) : Mono.error(new ValidationException("Formato de email inválido"));
    }

    private Mono<User> validarEmailUnico(User user) {
        return userRepository.findByEmail(user.getEmail())
                .flatMap(existing -> Mono.error(new ValidationException("Email ya registrado")))
                .switchIfEmpty(Mono.just(user)).cast(User.class);
    }
}
