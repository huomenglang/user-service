package com.menglang.user.service.dto.authenticate;

import lombok.Builder;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

@Builder
public record MeResponse(
        Long id,
        String username,
        String firstname,
        String lastname,
        String email,
        Set<String> roles,
        Set<String> permissions,
        Set<String> groups
) implements Serializable {

}
