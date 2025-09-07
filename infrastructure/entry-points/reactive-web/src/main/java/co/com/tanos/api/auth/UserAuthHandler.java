package co.com.tanos.api.auth;


import co.com.tanos.api.dto.request.LoginRequest;
import co.com.tanos.r2dbc.postgres_adapters.jwt.adapters.JwtUtil;
import co.com.tanos.r2dbc.postgres_adapters.jwt.entities.UserAuth;
import co.com.tanos.r2dbc.postgres_adapters.jwt.repositorys.UserAuthRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;



import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class UserAuthHandler {

    private final JwtUtil jwtUtil;
    private final UserAuthRepository userRepository;
    private final PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();

    public UserAuthHandler(JwtUtil jwtUtil, UserAuthRepository userRepository) {
        this.jwtUtil = jwtUtil;
        this.userRepository = userRepository;
    }

    public Mono<ServerResponse> createUser(ServerRequest request) {
        return request.bodyToMono(UserAuth.class)
                .flatMap(user -> {
                    user.setPassword(passwordEncoder.encode(user.getPassword()));
                    if (user.getRole() == null) {
                        user.setRole("USER");
                    }
                    return userRepository.save(user);
                })
                .flatMap(saved -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(saved));
    }

    public Mono<ServerResponse> login(ServerRequest request) {
        return request.bodyToMono(LoginRequest.class)
                .flatMap(loginRequest ->
                        userRepository.findByEmail(loginRequest.email())
                                .flatMap(user -> {
                                    if (passwordEncoder.matches(loginRequest.password(), user.getPassword())) {
                                        String token = JwtUtil.generateToken(user.getEmail(), user.getRole());
                                        Map<String, String> response = Collections.singletonMap("token", token);
                                        return ServerResponse.ok()
                                                .contentType(MediaType.APPLICATION_JSON)
                                                .bodyValue(response);
                                    } else {
                                        return ServerResponse.status(401).bodyValue("Credenciales inválidas");
                                    }
                                })
                                .switchIfEmpty(ServerResponse.status(401).bodyValue("Usuario no encontrado"))
                );
    }

    public Mono<ServerResponse> me(ServerRequest request) {
        return request.principal()
                .cast(Authentication.class)
                .flatMap(auth -> {
                    Map<String, Object> response = new HashMap<>();
                    response.put("email", auth.getName());
                    response.put("roles", auth.getAuthorities()
                            .stream()
                            .map(Object::toString)
                            .toList());

                    return ServerResponse.ok().bodyValue(response);
                });
    }
}
