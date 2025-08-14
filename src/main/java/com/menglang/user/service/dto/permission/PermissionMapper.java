package com.menglang.user.service.dto.permission;
import com.menglang.user.service.entity.model.Permission;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PermissionMapper {

    Permission toPermission(PermissionRequest request);
    PermissionResponse toResponse(Permission request);

}
