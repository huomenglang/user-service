package com.menglang.user.service.dto.role;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.io.Serializable;
import java.util.List;

public record RoleRequest(
        @Size(message = "Name must be between 3 and 5", max = 30, min = 3)
        String name,
        String description,
        @NotBlank(message = "Status must be Not Null")
        @Pattern(regexp = "^(ACTIVE|INACTIVE)$", message = "Status must be either ACTIVE or INACTIVE")
        String status,
        @NotNull(message = "Permission must be Not Null")
        List<Long> permissions
) implements Serializable {

}
