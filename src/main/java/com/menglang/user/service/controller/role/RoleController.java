package com.menglang.user.service.controller.role;
import com.menglang.common.library.page.PageResponse;
import com.menglang.common.library.page.PageResponseHandler;
import com.menglang.user.service.dto.role.RoleMapper;
import com.menglang.user.service.dto.role.RoleRequest;
import com.menglang.user.service.dto.role.RoleResponse;
import com.menglang.user.service.entity.model.Role;
import com.menglang.user.service.repository.RoleRepository;
import com.menglang.user.service.service.service.RoleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/roles")
@RequiredArgsConstructor
public class RoleController {
    private final RoleService roleService;
    private final RoleMapper roleMapper;
    private final RoleRepository roleRepository;

    @PostMapping
    public ResponseEntity<RoleResponse> create(@RequestBody @Valid RoleRequest dto) {
        return ResponseEntity.ok(roleService.create(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<RoleResponse> view(@PathVariable("id") Long id) {
        return ResponseEntity.ok(roleService.getById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<RoleResponse> update(@PathVariable("id") Long id, @Valid @RequestBody RoleRequest dto) {
        return ResponseEntity.ok(roleService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<RoleResponse> delete(@PathVariable("id") Long id) {
        return ResponseEntity.ok(roleService.delete(id));
    }

    @GetMapping
    public ResponseEntity<PageResponse> getAll(
            @RequestParam(name = "params") Map<String,String>params
    ) {
        Page<Role> rolePage = roleService.getAll(params);
        List<RoleResponse> roleResponseList=roleService.getPageContent(rolePage);
        return PageResponseHandler.success(roleResponseList, rolePage, "successful");
    }

}
