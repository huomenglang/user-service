package com.menglang.user.service.dto.permission;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.io.Serializable;

public record PermissionRequest(
        @NotBlank(message = "Name must be Not Null")
        @Size(message = "Name must be between 3 and 5", max = 30, min = 3)
        String name,
        String description,
        @NotBlank(message = "Status must be Not Null")
        @Pattern(regexp = "^(ACTIVE|INACTIVE)$", message = "Status must be either ACTIVE or INACTIVE")
        String status

) implements Serializable {
}
