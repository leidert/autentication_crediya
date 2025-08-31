package co.com.tanos.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;

@Schema(name = "UserRequest", description = "DTO para crear usuario")
public record UserRequest(

        @Schema(description = "nombres", example = "Leider ")
        Long dni,
        @Schema(description = "nombres", example = "Leider ")
       String name,
        @Schema(description = "apellidos", example = "Tanos")
       String lastName,
        @Schema(description = "fecha de nacimiento", example = "1/1/1111")
       LocalDate birdDate,
        @Schema(description = "direccion fisica", example = "555# abc")
       String address,
        @Schema(description = "numero de telefono", example = "+57 7777777")
       String phoneNumber,
        @Schema(description = "correo electronico", example = "tanos@gmail.com")
       String email,
        @Schema(description = "salario", example = "500000")
       Long salary
) {}