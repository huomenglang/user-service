package com.menglang.user.service.service.impl;
import com.menglang.common.library.exceptions.common.ConflictException;
import com.menglang.common.library.exceptions.common.NotFoundException;
import com.menglang.common.library.page.filter.FilterBy;
import com.menglang.common.library.page.parser.BaseSpecification;
import com.menglang.common.library.page.parser.PageableParser;
import com.menglang.common.library.page.parser.QueryParamParser;
import com.menglang.user.service.dto.permission.PermissionMapper;
import com.menglang.user.service.dto.permission.PermissionRequest;
import com.menglang.user.service.dto.permission.PermissionResponse;
import com.menglang.user.service.entity.model.Permission;
import com.menglang.user.service.exception.BaseException;
import com.menglang.user.service.repository.PermissionRepository;
import com.menglang.user.service.service.service.PermissionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class PermissionServiceImpl implements PermissionService {

    private final PermissionRepository permissionRepository;
    private final PermissionMapper mapper;


    @Override
    public PermissionResponse create(PermissionRequest request) {
        if (permissionRepository.existsByName(request.name())) {
            log.warn("config permission name");
            throw new ConflictException("Permission with name '" + request.name() + "' already exists");
        }
        Permission permission = mapper.toPermission(request);
        try{
           return mapper.toResponse(permissionRepository.save(permission));
        }catch (RuntimeException e){
            log.warn("Error create Permission {}",e.getMessage());
            throw new BaseException("Failed to Create Permission!");
        }

    }

    @Override
    public PermissionResponse update(Long id, PermissionRequest request) {
        Permission permission=this.findById(id);

        //check if new name conflicts with existing permission name(exclude current)
        if(!permission.getName().equals(request.name()) && permissionRepository.existsByName(request.name())){
            throw new ConflictException("Permission with name '" + request.name() + "' already exists");
        }

        permission.setName(request.name());
        permission.setDescription(request.description());
        permission.setStatus(request.status());

        try{
            return mapper.toResponse(permissionRepository.save(permission));
        }catch (RuntimeException e){
            log.warn("Error update Permission {}",e.getMessage());
            throw new BaseException("Failed to update Permission!");
        }

    }

    @Override
    public PermissionResponse delete(Long id) {
        Permission permission=this.findById(id);
        try{
            permissionRepository.deleteById(id);
            return mapper.toResponse(permission);
        }catch (RuntimeException e){
            log.warn("Error delete Permission {}",e.getMessage());
            throw new BaseException("Failed to delete Permission!");
        }
    }

    @Override
    public Page<Permission> getAll(Map<String, String> params) {
        Pageable pageable= PageableParser.from(params);
        List<FilterBy> filters= QueryParamParser.parse(params);
        Specification<Permission> spec=new BaseSpecification<>(filters);
        return permissionRepository.findAll(spec,pageable);
    }

    @Override
    public List<PermissionResponse> getPageContent(Page<Permission> page){
        return page.getContent().stream().map(mapper::toResponse).toList();
    }

    @Override
    public PermissionResponse getById(Long id) {
       Permission permission=this.findById(id);
        return mapper.toResponse(permission);
    }

    private Permission findById(Long id){
        return permissionRepository.findById(id).orElseThrow(()->new NotFoundException("Permission Not Found"));
    }
}
