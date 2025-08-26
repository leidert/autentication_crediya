package co.com.tanos.r2dbc;

import co.com.tanos.model.user.User;
import co.com.tanos.model.user.gateways.UserRepository;
import co.com.tanos.r2dbc.helper.ReactiveAdapterOperations;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Repository
public class MyReactiveRepositoryAdapter extends ReactiveAdapterOperations<
        User/* change for domain model */,
    UserEntity/* change for adapter model */,
    Long, MyReactiveRepository> implements UserRepository {

    public final MyReactiveRepository myReactiveRepository;

    public MyReactiveRepositoryAdapter(MyReactiveRepository repository, ObjectMapper mapper, MyReactiveRepository myReactiveRepository) {
        /**
         *  Could be use mapper.mapBuilder if your domain model implement builder pattern
         *  super(repository, mapper, d -> mapper.mapBuilder(d,ObjectModel.ObjectModelBuilder.class).build());
         *  Or using mapper.map with the class of the object model
         */
        super(repository, mapper, d -> mapper.map(d, User.class/* change for domain model */));
        this.myReactiveRepository = myReactiveRepository;
    }


    @Override
    public Mono<User> userRegister(User user) {
        return myReactiveRepository.save(toData(user)).map(this::toEntity);
    }

    @Override
    public Mono<User> findByEmail(String email) {
        return myReactiveRepository.findByEmail(email)
                .map(this::toEntity);
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
