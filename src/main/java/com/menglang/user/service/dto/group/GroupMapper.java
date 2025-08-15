package com.menglang.user.service.dto.group;

import com.menglang.common.library.exceptions.common.NotFoundException;
import com.menglang.user.service.entity.model.Group;
import com.menglang.user.service.entity.model.Permission;
import com.menglang.user.service.entity.model.Role;
import com.menglang.user.service.repository.PermissionRepository;
import com.menglang.user.service.repository.RoleRepository;
import org.mapstruct.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface GroupMapper {

        @Mapping(target = "permissions",source = "permissions",qualifiedByName = "toPermission")
        @Mapping(target = "roles",source ="roles",qualifiedByName = "toRole")
        Group toGroup(GroupRequest request, @Context RoleRepository roleRepository, @Context PermissionRepository permissionRepository);


        @Mapping(target = "permissions",expression = "java(mapToPermissions(entity.getPermissions()))")
        @Mapping(target = "roles",expression = "java(mapToRoles(entity.getRoles()))")
        GroupResponse toResponse(Group entity);

        @Mapping(target = "permissions",source = "permissions",qualifiedByName = "toPermission")
        @Mapping(target = "roles",source ="roles",qualifiedByName = "toRole")
        void updateGroup(GroupRequest request,@MappingTarget Group group,@Context RoleRepository roleRepository, @Context PermissionRepository permissionRepository);

        @Named("toRole")
        default Set<Role> toRole(List<Long> ids, @Context RoleRepository roleRepository){
                if (ids.isEmpty()) return new HashSet<>();
//               return ids.stream().map(id->findRoleById(id,roleRepository)).collect(Collectors.toSet());
                return new HashSet<>(roleRepository.findAllById(ids));
        }

        @Named("toPermission")
        default Set<Permission> toPermission(List<Long> ids, @Context PermissionRepository permissionRepository){
//                return ids.stream().map(per->findPermissionById(per,permissionRepository)).collect(Collectors.toSet());
                if (ids.isEmpty()) return new HashSet<>();
                return new HashSet<>(permissionRepository.findAllById(ids));
        }

        default Role findRoleById(Long id,@Context RoleRepository roleRepository){
                return roleRepository.findById(id).orElseThrow(()->new NotFoundException("Role Not Found!"));
        }
        default Permission findPermissionById(Long id,@Context PermissionRepository permissionRepository){
                return permissionRepository.findById(id).orElseThrow(()->new NotFoundException("Permission Not Found"));
        }

        @Named("mapToRoles")
        default Set<String> mapToRoles(Set<Role> roles){
                return roles.stream().map(Role::getName).collect(Collectors.toSet());
        }

        @Named("mapToPermissions")
        default Set<String> mapToPermissions(Set<Permission> permissions){
                return permissions.stream().map(Permission::getName).collect(Collectors.toSet());
        }
}
