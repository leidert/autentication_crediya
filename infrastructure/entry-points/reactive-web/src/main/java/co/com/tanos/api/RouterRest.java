package co.com.tanos.api;


import co.com.tanos.api.dto.UserRequest;
import co.com.tanos.api.dto.UserResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springdoc.core.annotations.RouterOperation;
import org.springdoc.core.annotations.RouterOperations;

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
    @RouterOperations({
            @RouterOperation(
                    path = "/api/users",
                    produces = { "application/json" },
                    method = RequestMethod.POST,
                    beanClass = Handler.class,
                    beanMethod = "createUser",
                    operation = @Operation(
                            operationId = "createUser",
                            summary = "Crear usuario",
                            description = "Crea un nuevo usuario en el sistema",
                            requestBody = @RequestBody(
                                    required = true,
                                    content = @Content(schema = @Schema(implementation = UserRequest.class))
                            ),
                            responses = {
                                    @ApiResponse(
                                            responseCode = "201",
                                            description = "Usuario creado",
                                            content = @Content(schema = @Schema(implementation = UserResponse.class))
                                    ),
                                    @ApiResponse(
                                            responseCode = "400",
                                            description = "Petición inválida"
                                    )
                            }
                    )
            ),
            @RouterOperation(
                    path = "/api/users/{id}",
                    produces = { "application/json" },
                    method = RequestMethod.GET,
                    beanClass = Handler.class,
                    beanMethod = "getUserByDni",
                    operation = @Operation(
                            operationId = "getUserByDni",
                            summary = "Obtener usuario por DNI",
                            description = "Obtiene un usuario específico por su DNI",
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
                                    )
                            }
                    )
            )
    })
    public RouterFunction<ServerResponse> routerFunction(Handler handler) {
        return route(POST("/api/users"), handler::createUser)
                .andRoute(GET("/api/users/{id}"), handler::getUserByDni);
    }
}
