package com.menglang.user.service.controller.permission;
import com.menglang.common.library.page.PageResponse;
import com.menglang.common.library.page.PageResponseHandler;
import com.menglang.user.service.dto.permission.PermissionRequest;
import com.menglang.user.service.dto.permission.PermissionResponse;
import com.menglang.user.service.entity.model.Permission;
import com.menglang.user.service.service.permission.PermissionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/v1/permissions")
@RequiredArgsConstructor
public class PermissionController {
    private final PermissionService permissionService;

    @PostMapping
    public ResponseEntity<PageResponse> create(@Valid @RequestBody PermissionRequest request){
        return PageResponseHandler.success(permissionService.create(request),null,"Create Success");
    }

    @GetMapping("/{id}")
    public ResponseEntity<PageResponse> view(@PathVariable Long id){
        return PageResponseHandler.success(permissionService.getById(id),null,"Get Success");
    }

    @PutMapping("/{id}")
    public ResponseEntity<PageResponse> update(@PathVariable Long id,@Valid @RequestBody PermissionRequest request){
        return PageResponseHandler.success(permissionService.update(id,request),null,"Update Success");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<PageResponse> delete(@PathVariable Long id){
        return PageResponseHandler.success(permissionService.delete(id),null,"Delete Success");
    }

    @GetMapping("/get-all")
    public ResponseEntity<PageResponse> getAll( @RequestParam Map<String,String> params){
        Page<Permission> permissionsPage=permissionService.getAll(params);
        List<PermissionResponse> permissionResponses=permissionService.getPageContent(permissionsPage);
        return PageResponseHandler.success(permissionResponses,permissionsPage,"Delete Success");
    }


}
