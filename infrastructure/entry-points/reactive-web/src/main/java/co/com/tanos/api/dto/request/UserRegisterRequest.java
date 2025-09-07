package co.com.tanos.api.dto.request;

import co.com.tanos.model.user.enums.RoleOld;

public record UserRegisterRequest(
        String email,
        String password,
        RoleOld roleOld
) {
}
