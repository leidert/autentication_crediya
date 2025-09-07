package co.com.tanos.api.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "UserResponse", description = "Respuesta del endpoint de usuario")
public record UserResponse(
        @Schema(description = "Nombre completo del usuario", example = "Leider")
        Long dni,
        @Schema(description = "Nombre completo del usuario", example = "Leider")
        String name,
        @Schema(description = "Nombre completo del usuario", example = "Tanos")
        String email
) {}