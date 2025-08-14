package com.menglang.user.service.service.service;

import com.menglang.user.service.dto.role.RoleRequest;
import com.menglang.user.service.dto.role.RoleResponse;
import com.menglang.user.service.entity.model.Role;

import java.util.List;
import java.util.Set;

public interface RoleService extends BaseService<RoleRequest, RoleResponse, Role>{
   List<RoleResponse> getRoleByNameIn(Set<String> names);
}
