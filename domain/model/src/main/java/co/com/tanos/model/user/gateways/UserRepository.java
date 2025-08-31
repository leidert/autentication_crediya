package co.com.tanos.model.user.gateways;

import co.com.tanos.model.user.User;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public interface UserRepository {

    public Mono<User> userRegister(User user);

    public Flux<List<User>> getAllUser();

    public Mono<User> getUserFindById(Long id);

    Mono<User> findByEmail(String email);

    Mono<User> findByDni(Long dni);



}
