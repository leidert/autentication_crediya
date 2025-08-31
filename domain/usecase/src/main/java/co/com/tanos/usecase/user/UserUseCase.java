package co.com.tanos.usecase.user;

import co.com.tanos.model.user.User;
import co.com.tanos.usecase.user.exceptions.UserValidator;
import co.com.tanos.model.user.gateways.UserRepository;
import lombok.RequiredArgsConstructor;


import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;


@RequiredArgsConstructor
public class UserUseCase {

    public final UserRepository userRepository;
    public final UserValidator userValidator;


    public Mono<User> useCaseUserRegister(User user) {
        return Mono.just(user)
                .flatMap(userValidator::validateRequiredFields)
                .flatMap(userValidator::validateFormateEmail)
                .flatMap(userValidator::validateSingleEmail)
                .flatMap(userValidator::validateSingleDni)
                .flatMap(userRepository::userRegister);
    }

    public Mono<User> getUserFindByDni(Long dni) {
        return  userValidator.validateEmptyDni(dni);
    }


    public Flux<List<User>> UseCaseGetAllUser() {
        return userRepository.getAllUser();
    }


    public Mono<User> getUserFindById(Long id) {
        return userRepository.getUserFindById(id);
    }
}