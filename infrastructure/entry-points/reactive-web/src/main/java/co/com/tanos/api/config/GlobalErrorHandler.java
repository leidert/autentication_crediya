package co.com.tanos.api.config;

import co.com.tanos.model.user.ValidationException;
import io.micrometer.common.lang.NonNull;
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.codec.HttpMessageWriter;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.HandlerStrategies;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.reactive.result.view.ViewResolver;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;

@Component
@Order(-2)
public class GlobalErrorHandler implements ErrorWebExceptionHandler {

    @Override
    public Mono<Void> handle(ServerWebExchange exchange, Throwable ex) {
        Mono<ServerResponse> response;

        if (ex instanceof ValidationException) {
            response = ServerResponse.status(HttpStatus.BAD_REQUEST)
                    .bodyValue(Map.of("error", ex.getMessage()));
        } else {
            response = ServerResponse.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .bodyValue(Map.of("error", "Error interno del servidor"));
        }

        return response.flatMap(resp -> resp.writeTo(exchange, new DefaultContext()));
    }

    private static class DefaultContext implements ServerResponse.Context {

        @Override
        @NonNull
        public List<HttpMessageWriter<?>> messageWriters() {
            return HandlerStrategies.withDefaults().messageWriters();
        }

        @Override
        @NonNull
        public List<ViewResolver> viewResolvers() {
            return List.of();
        }
    }
}
