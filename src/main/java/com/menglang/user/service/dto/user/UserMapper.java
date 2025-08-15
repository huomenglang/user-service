package com.menglang.user.service.dto.user;

import com.menglang.user.service.entity.model.Group;
import com.menglang.user.service.entity.model.Permission;
import com.menglang.user.service.entity.model.Role;
import com.menglang.user.service.entity.model.User;
import com.menglang.user.service.repository.GroupRepository;
import com.menglang.user.service.repository.RoleRepository;
import org.mapstruct.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {

    @Mapping(target = "roles",source ="roles",qualifiedByName = "toRoles")
    @Mapping(target = "groups",source = "groups",qualifiedByName = "toGroups")
    User toUser(UserRequest request, @Context RoleRepository roleRepository, @Context GroupRepository groupRepository);

    @Mapping(target = "groups",expression = "java(mapToGroups(user.getGroups()))")
    @Mapping(target = "roles",expression = "java(mapToRoles(user.getRoles()))")
    UserResponse toResponse(User user);

    @Mapping(target = "roles",source ="roles",qualifiedByName = "toRoles")
    @Mapping(target = "groups",source = "groups",qualifiedByName = "toGroups")
    void updateUser(UserRequest request,@MappingTarget User user,@Context RoleRepository roleRepository, @Context GroupRepository groupRepository);


    @Named("toRoles")
    default Set<Role> toRole(List<Long> ids, @Context RoleRepository roleRepository){
        if (ids.isEmpty()) return new HashSet<>();
        return new HashSet<>(roleRepository.findAllById(ids));
    }

    @Named("toGroups")
    default Set<Group> toGroups(List<Long> ids,@Context GroupRepository groupRepository){
        if (ids.isEmpty()) return new HashSet<>();
        return new HashSet<>(groupRepository.findAllById(ids));
    }

    @Named("mapToRoles")
    default Set<String> mapToRoles(Set<Role> roles){
        return roles.stream().map(Role::getName).collect(Collectors.toSet());
    }

    @Named("mapToGroups")
    default Set<String> mapToGroups(Set<Group> groups){
        return groups.stream().map(Group::getName).collect(Collectors.toSet());
    }

}
