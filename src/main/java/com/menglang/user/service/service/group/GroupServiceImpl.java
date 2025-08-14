package com.menglang.user.service.service.group;

import com.menglang.common.library.exceptions.common.BadRequestException;
import com.menglang.common.library.exceptions.common.ConflictException;
import com.menglang.common.library.exceptions.common.NotFoundException;
import com.menglang.common.library.page.filter.FilterBy;
import com.menglang.common.library.page.parser.BaseSpecification;
import com.menglang.common.library.page.parser.PageableParser;
import com.menglang.common.library.page.parser.QueryParamParser;
import com.menglang.user.service.dto.group.GroupMapper;
import com.menglang.user.service.dto.group.GroupMemberRequest;
import com.menglang.user.service.dto.group.GroupRequest;
import com.menglang.user.service.dto.group.GroupResponse;
import com.menglang.user.service.entity.model.Group;
import com.menglang.user.service.entity.model.User;
import com.menglang.user.service.repository.GroupRepository;
import com.menglang.user.service.repository.PermissionRepository;
import com.menglang.user.service.repository.RoleRepository;
import com.menglang.user.service.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class GroupServiceImpl implements GroupService {
    private final UserRepository userRepository;

    private final GroupRepository groupRepository;
    private final PermissionRepository permissionRepository;
    private final GroupMapper groupMapper;
    private final RoleRepository roleRepository;

    @Override
    public GroupResponse createGroup(GroupRequest request) {

        if (groupRepository.existsByName(request.name(),null))
            throw new ConflictException("Group already Exist!");
        Group group=groupMapper.toGroup(request,roleRepository,permissionRepository);
        try{
            return groupMapper.toResponse(groupRepository.save(group));
        }catch (RuntimeException e){
            log.warn("Cannot Create Group: {}",e.getMessage());
            throw new BadRequestException("Cannot Create Group!");
        }

    }

    @Override
    public GroupResponse updateGroup(Long id, GroupRequest request) {
        Group groupUpdate=this.findById(id);
        if (groupRepository.existsByName(request.name(),id))
            throw new ConflictException("Group already Exist!");
        groupMapper.updateGroup(request,groupUpdate,roleRepository,permissionRepository);
        try{
            return groupMapper.toResponse(groupRepository.save(groupUpdate));
        }catch (RuntimeException e){
            log.warn("Cannot Update Group: {}",e.getMessage());
            throw new BadRequestException("Cannot Update Group!");
        }

    }

    @Override
    public GroupResponse addMembersToGroup(Long groupId, GroupMemberRequest groupMemberRequest) {
        Group group=this.findById(groupId);
        List<User> users=userRepository.findAllById(groupMemberRequest.userIds());
        for (User user:users){
            if(!group.getUsers().contains(user)){
                group.addUser(user);
            }
        }

        Group updateGroup=groupRepository.save(group);
         return groupMapper.toResponse(updateGroup);
    }

    @Override
    public GroupResponse removeMembersFromGroup(Long groupId, GroupMemberRequest groupMemberRequest) {
        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new RuntimeException("Group not found with id: " + groupId));

        List<User> users = userRepository.findAllById(groupMemberRequest.userIds());

        for (User user : users) {
            group.removeUser(user);
        }

        Group updatedGroup = groupRepository.save(group);
        return groupMapper.toResponse(updatedGroup);
    }

    @Override
    public Page<Group> getAllGroups(Map<String, String> params) {
        Pageable pageable= PageableParser.from(params);
        List<FilterBy> filterByList= QueryParamParser.parse(params);
        Specification<Group> spec=new BaseSpecification<>(filterByList);
        return groupRepository.findAll(spec,pageable);
    }

    @Override
    public GroupResponse getGroupById(Long id) {
        return groupMapper.toResponse(this.findById(id));
    }

    @Override
    public List<GroupResponse> getPageContent(Page<Group> groupPage) {
       return groupPage.getContent().stream().map(groupMapper::toResponse).toList();
    }

    private Group findById(Long id){
        return groupRepository.findById(id).orElseThrow(()->new NotFoundException("Group Not Found!"));
    }
}
