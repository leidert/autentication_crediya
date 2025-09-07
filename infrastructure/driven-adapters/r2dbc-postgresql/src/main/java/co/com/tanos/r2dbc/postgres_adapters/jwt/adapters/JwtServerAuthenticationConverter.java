package co.com.tanos.r2dbc.postgres_adapters.jwt.adapters;

import org.springframework.security.web.server.authentication.ServerAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.BearerTokenAuthenticationToken;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import org.springframework.security.core.Authentication;


public class JwtServerAuthenticationConverter implements ServerAuthenticationConverter{

    @Override
    public Mono<Authentication> convert(ServerWebExchange exchange) {
        String authHeader = exchange.getRequest().getHeaders().getFirst("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            return Mono.just(new BearerTokenAuthenticationToken(token));
        }
        return Mono.empty();
    }
}
