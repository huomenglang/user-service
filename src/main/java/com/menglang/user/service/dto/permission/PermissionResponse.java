package com.menglang.user.service.dto.permission;


import java.time.LocalDateTime;

public record PermissionResponse(
        Long id,
        String name,
        String description,
        String status,
        LocalDateTime createdAt,
        String createdBy,
        LocalDateTime updatedAt,
        String updatedBy
){
}
