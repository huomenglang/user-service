package com.menglang.user.service.dto.role;

import com.menglang.user.service.dto.permission.PermissionResponse;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
public record RoleResponse(
        Long id,
        String name,
        String description,
        String status,
        List<PermissionResponse> permissions,
        LocalDateTime createdAt,
        String createdBy,
        LocalDateTime updatedAt,
        String updatedBy
) implements Serializable {
}
