package com.menglang.user.service.dto.authenticate;

import jakarta.validation.constraints.NotNull;

public record AuthenticationRequest(
        @NotNull(message = "Username Must not be null!")
        String username,
        @NotNull(message = "Password must not be null!")
        String password
) {
}
