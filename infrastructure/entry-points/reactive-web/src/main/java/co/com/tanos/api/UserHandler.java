package co.com.tanos.api;

import co.com.tanos.model.user.User;
import co.com.tanos.api.dto.request.UserRequest;
import co.com.tanos.api.dto.response.UserResponse;
import co.com.tanos.usecase.user.UserUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class UserHandler {

private  final UserUseCase useCase;


    public Mono<ServerResponse> createUser(ServerRequest request) {
        return request.bodyToMono(UserRequest.class)
                .flatMap(userRequest -> {
                    User user = User.builder().dni(userRequest.dni()).name(userRequest.name()).lastName(userRequest.lastName()).address(userRequest.address())
                            .email(userRequest.email()).salary(userRequest.salary()).birdDate(userRequest.birdDate()).phoneNumber(userRequest.phoneNumber())
                            .build();
                    return useCase.useCaseUserRegister(user);
                })
                .map(user -> new UserResponse(user.getDni(), user.getName(), user.getEmail()))
                .flatMap(userResponse -> ServerResponse
                        .status(HttpStatus.CREATED)
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(userResponse));
    }

    public Mono<ServerResponse> getUserByDni(ServerRequest request) {
        Long id = Long.valueOf(request.pathVariable("id"));
        return useCase.getUserFindByDni(id)
                .map(user -> new UserResponse(user.getDni(), user.getName(), user.getEmail()))
                .flatMap(user -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(user))
                .switchIfEmpty(ServerResponse.notFound().build());
    }

}
