package com.menglang.user.service.dto.authenticate;

import com.fasterxml.jackson.annotation.JsonProperty;

public record AuthenticateResponse(
        @JsonProperty("access_token") String accessToken,
        @JsonProperty("refresh_token") String refreshToken
) {
}
