package co.com.tanos.r2dbc.postgres_adapters.jwt.config;

import co.com.tanos.r2dbc.postgres_adapters.jwt.adapters.JwtAuthenticationManager;
import co.com.tanos.r2dbc.postgres_adapters.jwt.adapters.JwtServerAuthenticationConverter;
import co.com.tanos.r2dbc.postgres_adapters.jwt.adapters.JwtUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.web.server.authentication.AuthenticationWebFilter;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {
    private final JwtUtil jwtUtil = new JwtUtil();

    @Bean
    public ReactiveAuthenticationManager reactiveAuthenticationManager() {
        return new JwtAuthenticationManager(jwtUtil);
    }

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        AuthenticationWebFilter jwtFilter =
                new AuthenticationWebFilter(reactiveAuthenticationManager());
        jwtFilter.setServerAuthenticationConverter(new JwtServerAuthenticationConverter());

        return http
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .httpBasic(ServerHttpSecurity.HttpBasicSpec::disable)
                .formLogin(ServerHttpSecurity.FormLoginSpec::disable)
                .authorizeExchange(exchange -> exchange
                        .pathMatchers(HttpMethod.POST, "/api/auth/login").permitAll()
                        .pathMatchers(HttpMethod.POST, "/api/auth/user").permitAll()
                        .pathMatchers("/api/user").hasRole("ADMIN")
                        .pathMatchers("/api/users/{id}").hasRole("ADMIN")
                        .pathMatchers(HttpMethod.GET, "/api/auth/user").hasRole("ADMIN")
                        .anyExchange().authenticated()
                )
                .addFilterAt(jwtFilter, SecurityWebFiltersOrder.AUTHENTICATION) // 👈 agrega tu filtro JWT
                .build();
    }

    @Bean
    public JwtUtil jwtUtil() {
        return jwtUtil;
    }
}
