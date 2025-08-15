package com.menglang.user.service.service.seeding;

import com.menglang.common.library.exceptions.common.BadRequestException;
import com.menglang.user.service.dto.group.GroupMapper;
import com.menglang.user.service.dto.group.GroupRequest;
import com.menglang.user.service.dto.permission.PermissionMapper;
import com.menglang.user.service.dto.permission.PermissionRequest;
import com.menglang.user.service.dto.role.RoleMapper;
import com.menglang.user.service.dto.role.RoleRequest;
import com.menglang.user.service.dto.user.UserMapper;
import com.menglang.user.service.dto.user.UserRequest;
import com.menglang.user.service.entity.model.Group;
import com.menglang.user.service.entity.model.Permission;
import com.menglang.user.service.entity.model.Role;
import com.menglang.user.service.entity.model.User;
import com.menglang.user.service.repository.GroupRepository;
import com.menglang.user.service.repository.PermissionRepository;
import com.menglang.user.service.repository.RoleRepository;
import com.menglang.user.service.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
public class Seeding {
    private final PermissionRepository permissionRepository;
    private final PermissionMapper permissionMapper;
    private final RoleRepository roleRepository;
    private final RoleMapper roleMapper;
    private final GroupRepository groupRepository;
    private final GroupMapper groupMapper;
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @PostConstruct
    private void seeding(){
        seedPermission();
        seedRoles();
        seedGroup();
        seedUser();
    }

    private void seedRoles() {
        log.info(" ----------------------seeding role......................");
        List<String> roles = List.of("USER", "ADMIN", "SUPER_ADMIN");
        List<Role> roleList = new ArrayList<>();
        for (String role : roles) {
            RoleRequest r = new RoleRequest(role, role, "ACTIVE",List.of(3L,7L,9L,11L));
            Role reqRole = roleMapper.toRole(r, permissionRepository);
            roleList.add(reqRole);
        }
        roleRepository.saveAll(roleList);
    }
    private void seedGroup(){
        log.info(" ----------------------seeding group......................");
        List<String> groups=List.of("IT Application","IT Network","IT Infrastructure","IT Support");
        List<Group> groupList=new ArrayList<>();

        for (String group:groups){
            GroupRequest groupRequest=new GroupRequest(group,group,"ACTIVE",List.of(1L,2L,3L),List.of(1L,5L));
            Group groupEntity=groupMapper.toGroup(groupRequest,roleRepository,permissionRepository);
            groupList.add(groupEntity);
        }
        groupRepository.saveAll(groupList);
    }

    private void seedPermission() {

        log.info("---------------------Seeding Permission-----------------------------");
        List<String> crudData = List.of("VIEW", "CREATE", "UPDATE", "DELETE");
        List<String> entities = List.of("CLASSROOM", "STUDENT", "REPORT", "TEACHER", "EXAM");
        List<Permission> permissionRequestsList = new ArrayList<>();

        for (String crud : crudData) {
            for (String entity : entities) {
                String permission = crud + "_" + entity;
                String description = crud + " " + entity;

                PermissionRequest permissionRequest =new PermissionRequest(permission,description,"ACTIVE");

                Permission permissionData = permissionMapper.toPermission(permissionRequest);
                permissionRequestsList.add(permissionData);
            }
        }
        try {
            permissionRepository.saveAll(permissionRequestsList);
        } catch (Exception e) {
            throw new BadRequestException(e.getMessage());
        }

    }
    private void seedUser(){
        log.info("---------------------Seeding users-----------------------------");
        String pwd=passwordEncoder.encode("lang@123");
        UserRequest userRequest=new UserRequest("menglang","Menglang","Huo",
                "N/A",pwd,"menglang@gmail.com","ADMIN",
                "MALE","2022-01-01","ACTIVE",List.of(3L),List.of(1L)
                );

        User userSave=userMapper.toUser(userRequest,roleRepository,groupRepository);
        try{
            userRepository.save(userSave);
        }catch (RuntimeException e){
            log.warn("Unable to create User {}",e.getMessage());
        }
    }
}
