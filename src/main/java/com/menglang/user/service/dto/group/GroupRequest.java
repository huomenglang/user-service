package com.menglang.user.service.dto.group;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.Set;

public record GroupRequest(
        @NotNull(message = "Group name is required")
        @Size(min = 2, max = 50, message = "Group name must be between 2 and 50 characters")
        String name,

        @Size(max = 500, message = "Description cannot exceed 500 characters")
        String description,

        Set<Long> roles,

        Set<Long> permissions,

        String status
) {
}
