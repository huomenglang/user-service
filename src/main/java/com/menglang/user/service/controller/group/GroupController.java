package com.menglang.user.service.controller.group;
import com.menglang.common.library.page.PageResponse;
import com.menglang.common.library.page.PageResponseHandler;
import com.menglang.user.service.dto.group.GroupMemberRequest;
import com.menglang.user.service.dto.group.GroupRequest;
import com.menglang.user.service.dto.group.GroupResponse;
import com.menglang.user.service.entity.model.Group;
import com.menglang.user.service.service.group.GroupService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/groups")
public class GroupController {

    private final GroupService groupService;

    public GroupController(GroupService groupService) {
        this.groupService = groupService;
    }

    @PostMapping
    public ResponseEntity<GroupResponse> createGroup(@Valid @RequestBody GroupRequest request) {
        return ResponseEntity.ok(groupService.createGroup(request));
    }


    @PutMapping("/{groupId}")
    public ResponseEntity<GroupResponse> updateGroup(
            @PathVariable Long groupId,
            @Valid @RequestBody GroupRequest request) {
        return ResponseEntity.ok(groupService.updateGroup(groupId, request));
    }

    @PostMapping("/{groupId}/members")
    public ResponseEntity<GroupResponse> addMembersToGroup(
            @PathVariable Long groupId,
            @Valid @RequestBody GroupMemberRequest request) {
        return ResponseEntity.ok(groupService.addMembersToGroup(groupId, request));
    }

    @DeleteMapping("/{groupId}/members")
    public ResponseEntity<GroupResponse> removeMembersFromGroup(
            @PathVariable Long groupId,
            @Valid @RequestBody GroupMemberRequest request) {
        return ResponseEntity.ok(groupService.removeMembersFromGroup(groupId, request));
    }

    @GetMapping("get-all")
    public ResponseEntity<PageResponse> getAllGroups(@RequestParam Map<String,String> params){
        Page<Group> groupPage=groupService.getAllGroups(params);
        List<GroupResponse> groupResponses=groupService.getPageContent(groupPage);
        return PageResponseHandler.success(groupResponses,groupPage,"Get success");
    }

} 