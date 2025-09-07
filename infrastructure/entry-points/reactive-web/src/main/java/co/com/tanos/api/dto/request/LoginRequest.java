package co.com.tanos.api.dto.request;

public record LoginRequest(
        String email,
        String password
) {
}
