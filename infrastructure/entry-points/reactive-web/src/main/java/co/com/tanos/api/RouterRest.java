package co.com.tanos.api;


import co.com.tanos.api.auth.UserAuthHandler;
import co.com.tanos.api.dto.response.UserResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springdoc.core.annotations.RouterOperation;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class RouterRest {

    @Bean
    @RouterOperation(
            path = "/api/users/{id}",
            produces = { "application/json" },
            method = RequestMethod.GET,
            beanClass = UserHandler.class,
            beanMethod = "getUserByDni",
            operation = @Operation(
                    operationId = "getUserByDni",
                    summary = "Obtener usuario por DNI",
                    description = "Obtiene un usuario específico por su DNI",
                    // Aquí hereda la seguridad global (JWT)
                    parameters = {
                            @Parameter(
                                    name = "id",
                                    description = "DNI del usuario",
                                    required = true,
                                    in = ParameterIn.PATH,
                                    schema = @Schema(type = "string")
                            )
                    },
                    responses = {
                            @ApiResponse(
                                    responseCode = "200",
                                    description = "Usuario encontrado",
                                    content = @Content(schema = @Schema(implementation = UserResponse.class))
                            ),
                            @ApiResponse(
                                    responseCode = "404",
                                    description = "Usuario no encontrado"
                            ),
                            @ApiResponse(
                                    responseCode = "401",
                                    description = "No autorizado"
                            )
                    }
            )
    )
    public RouterFunction<ServerResponse> routerFunction(UserHandler userHandler, UserAuthHandler handler) {
        return route(POST("/api/users"), userHandler::createUser)
                .andRoute(GET("/api/users/{id}"), userHandler::getUserByDni)
                //.andRoute(POST("/api/auth/register"), handler::register)
                .andRoute(GET("/api/auth/user"), handler::me)
                .andRoute(POST("/api/auth/user"), handler::createUser)
                .andRoute(POST("/api/auth/login"), handler::login);
    }
}
