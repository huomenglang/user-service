package com.menglang.user.service.service.role;

import com.menglang.common.library.exceptions.common.BadRequestException;
import com.menglang.common.library.exceptions.common.ConflictException;
import com.menglang.common.library.exceptions.common.NotFoundException;
import com.menglang.common.library.page.filter.FilterBy;
import com.menglang.common.library.page.parser.BaseSpecification;
import com.menglang.common.library.page.parser.PageableParser;
import com.menglang.common.library.page.parser.QueryParamParser;
import com.menglang.user.service.dto.role.RoleMapper;
import com.menglang.user.service.dto.role.RoleRequest;
import com.menglang.user.service.dto.role.RoleResponse;
import com.menglang.user.service.entity.model.Role;
import com.menglang.user.service.repository.PermissionRepository;
import com.menglang.user.service.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;
    private final PermissionRepository permissionRepository;
    private final RoleMapper roleMapper;

    @Override
    public List<RoleResponse> getRoleByNameIn(Set<String> names) {
        return roleRepository.findAllByNameIn(names).stream().map(roleMapper::toResponse).toList();
    }

    @Override
    public RoleResponse create(RoleRequest dto) {
        // Check for duplicate role name
        if (roleRepository.findFirstByName(dto.name()).isPresent()) {
            throw new ConflictException("Role name already exists");
        }


        Role role = roleMapper.toRole(dto, permissionRepository);
        try {
            log.info(" role {}",role.getName());
            Role roleSaved = roleRepository.save(role);
            return roleMapper.toResponse(roleSaved);
        } catch (Exception e) {
            throw new BadRequestException(e.getMessage());
        }
    }
    @Override
    public RoleResponse update(Long id, RoleRequest dto) {
        Role role=this.findById(id);
        if(roleRepository.findByNameAndIdNotIn(dto.name(), id))
            throw new ConflictException("Role name already exists");
        roleMapper.updateToRole(dto,role,permissionRepository);
        try {
            Role roleUpdate= roleRepository.save(role);
            return roleMapper.toResponse(roleUpdate);
        }catch (Exception e){
            throw new BadRequestException(e.getMessage());
        }
    }

    @Override
    public RoleResponse delete(Long id) {
        Role role=findById(id);
        try{
            roleRepository.delete(role);
            return roleMapper.toResponse(role);
        }catch (Exception e){
            throw new BadRequestException(e.getMessage());
        }
    }

    @Override
    public RoleResponse getById(Long id) {
        return roleMapper.toResponse(findById(id));
    }

    @Override
    public List<RoleResponse> getPageContent(Page<Role> data) {
        return data.getContent().stream().map(roleMapper::toResponse).toList();
    }

    @Override
    public Page<Role> getAll(Map<String, String> params) {
        Pageable pageable= PageableParser.from(params);
        List<FilterBy> filters= QueryParamParser.parse(params);
        Specification<Role> spec=new BaseSpecification<>(filters);
        return roleRepository.findAll(spec,pageable);

    }
    private Role findById(Long id){
        return roleRepository.findById(id).orElseThrow(()->new NotFoundException("Role Not Found."));
    }
}
