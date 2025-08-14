package com.menglang.user.service.dto.role;

import com.menglang.common.library.exceptions.common.NotFoundException;
import com.menglang.user.service.dto.permission.PermissionResponse;
import com.menglang.user.service.entity.model.Permission;
import com.menglang.user.service.entity.model.Role;
import com.menglang.user.service.repository.PermissionRepository;
import org.mapstruct.*;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface RoleMapper {

    @Mapping(target = "permissions",source = "permissions",qualifiedByName = "mapToPermission")

    Role toRole(RoleRequest dto, @Context PermissionRepository repository);

    @Mapping(target = "permissions",source = "permissions",qualifiedByName = "mapToPermission")
    void updateToRole(RoleRequest dto, @MappingTarget Role role, @Context PermissionRepository repository);

    @Mapping(target = "permissions",source = "permissions",qualifiedByName = "mapToPermissionRes")
    RoleResponse toResponse(Role role);

    PermissionResponse toPermissionRes(Permission permission);

    @Named("mapToPermission")
    default Permission mapToPermission(Long id,@Context PermissionRepository repository){
        return repository.findById(id).orElseThrow(()->new NotFoundException("Permission Not Found."));
    }

    @Named("mapToPermissionRes")
    default PermissionResponse mapToPermissionRes(Permission permission){
        return this.toPermissionRes(permission);
    }
}
