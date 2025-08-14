package com.menglang.user.service.service.group;

import com.menglang.user.service.dto.group.GroupMemberRequest;
import com.menglang.user.service.dto.group.GroupRequest;
import com.menglang.user.service.dto.group.GroupResponse;
import com.menglang.user.service.entity.model.Group;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Map;

public interface GroupService {
    GroupResponse createGroup(GroupRequest request);

    GroupResponse updateGroup(Long id, GroupRequest request);

    GroupResponse addMembersToGroup(Long groupId, GroupMemberRequest groupMemberRequest);

    GroupResponse removeMembersFromGroup(Long groupId, GroupMemberRequest groupMemberRequest);

    Page<Group> getAllGroups(Map<String,String> params);
    GroupResponse getGroupById(Long id);
    List<GroupResponse> getPageContent(Page<Group> groupPage);
}
