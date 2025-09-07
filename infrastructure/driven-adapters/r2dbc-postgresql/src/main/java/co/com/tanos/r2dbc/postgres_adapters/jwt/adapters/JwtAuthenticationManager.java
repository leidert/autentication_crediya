package co.com.tanos.r2dbc.postgres_adapters.jwt.adapters;

import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.server.resource.authentication.BearerTokenAuthenticationToken;
import reactor.core.publisher.Mono;

public class JwtAuthenticationManager implements ReactiveAuthenticationManager {

    private final JwtUtil jwtUtil;

    public JwtAuthenticationManager(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    public Mono<Authentication> authenticate(Authentication authentication) {

        if (!(authentication instanceof BearerTokenAuthenticationToken bearer)) {
            return Mono.empty();
        }

        String token = bearer.getToken();

        if (!jwtUtil.validateToken(token)) {
            return Mono.empty();
        }

        String username = jwtUtil.getUsernameFromToken(token);

        var authorities = jwtUtil.getRolesFromToken(token)
                .stream()
                .map(r -> "ROLE_" + r)
                .map(org.springframework.security.core.authority.SimpleGrantedAuthority::new)
                .toList();

        Authentication authResult =
                new UsernamePasswordAuthenticationToken(username, null, authorities);
        System.out.println("Authorities del token: " + authorities);

        return Mono.just(authResult);
    }
}
