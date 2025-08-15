package com.menglang.user.service.dto.authenticate;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record AuthenticateResponse(String username,
                                String firstname,
                                String lastname,
                                String email,
                                List<String> roles,
                                @JsonProperty("access_token") String accessToken,
                                @JsonProperty("refresh_token") String refreshToken) {
}

