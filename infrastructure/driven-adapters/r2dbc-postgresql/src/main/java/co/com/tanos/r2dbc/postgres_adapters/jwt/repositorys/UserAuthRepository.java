package co.com.tanos.r2dbc.postgres_adapters.jwt.repositorys;

import co.com.tanos.r2dbc.postgres_adapters.jwt.entities.UserAuth;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface UserAuthRepository extends ReactiveCrudRepository<UserAuth, Long> {
    Mono<UserAuth> findByEmail(String email);
}
