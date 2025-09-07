package co.com.tanos.r2dbc.postgres_adapters.adapters;

import co.com.tanos.model.user.User;
import co.com.tanos.model.user.gateways.UserRepository;
import co.com.tanos.r2dbc.helper.ReactiveAdapterOperations;
import co.com.tanos.r2dbc.postgres_adapters.entities.UserEntity;
import co.com.tanos.r2dbc.postgres_adapters.repositorys.UserReactiveRepository;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.reactive.TransactionalOperator;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Repository
public class UserReactiveRepositoryAdapter extends ReactiveAdapterOperations<
        User/* change for domain model */,
        UserEntity/* change for adapter model */,
    Long, UserReactiveRepository> implements UserRepository {

    public final UserReactiveRepository userReactiveRepository;
    private final TransactionalOperator transactionalOperator;

    public UserReactiveRepositoryAdapter(UserReactiveRepository repository, ObjectMapper mapper, UserReactiveRepository userReactiveRepository, TransactionalOperator transactionalOperator) {
        super(repository, mapper, d -> mapper.map(d, User.class/* change for domain model */));
        this.userReactiveRepository = userReactiveRepository;
        this.transactionalOperator = transactionalOperator;
    }


    @Override
    public Mono<User> userRegister(User user) {
        return userReactiveRepository.save(toData(user)).map(this::toEntity)
                .as(transactionalOperator::transactional);
    }

    @Override
    public Mono<User> findByEmail(String email) {
        return userReactiveRepository.findByEmail(email)
                .map(this::toEntity).as(transactionalOperator::transactional);
    }

    @Override
    public Mono<User> findByDni(Long dni) {
        return userReactiveRepository.findByDni(dni)
                .map(this::toEntity).as(transactionalOperator::transactional);
    }

    @Override
    public Flux<List<User>> getAllUser() {
        return null;
    }

    @Override
    public Mono<User> getUserFindById(Long id) {
        return null;
    }
}
