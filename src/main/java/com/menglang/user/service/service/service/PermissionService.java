package com.menglang.user.service.service.service;


import com.menglang.user.service.dto.permission.PermissionRequest;
import com.menglang.user.service.dto.permission.PermissionResponse;
import com.menglang.user.service.entity.model.Permission;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Map;


public interface PermissionService {
    PermissionResponse create(PermissionRequest request);
    PermissionResponse update( Long id, PermissionRequest request);
    PermissionResponse delete(Long id);
    Page<Permission> getAll(Map<String,String> params);
    PermissionResponse getById(Long id);
    List<PermissionResponse> getPageContent(Page<Permission> permissions);
}
