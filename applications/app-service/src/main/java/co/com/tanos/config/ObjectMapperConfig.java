package co.com.tanos.config;

import co.com.tanos.usecase.user.exceptions.UserValidator;
import co.com.tanos.model.user.gateways.UserRepository;
import co.com.tanos.usecase.user.exceptions.UserValidatorImpl;
import org.reactivecommons.utils.ObjectMapper;
import org.reactivecommons.utils.ObjectMapperImp;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ObjectMapperConfig {

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapperImp();
    }
    @Bean
    public UserValidator userValidator(UserRepository userRepository) {
        return new UserValidatorImpl(userRepository);
    }

}
