package com.menglang.user.service.dto.permission;
import com.menglang.user.service.entity.model.Permission;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PermissionMapper {

    Permission toPermission(PermissionRequest request);
    PermissionResponse toResponse(Permission request);

}
