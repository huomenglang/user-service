package com.menglang.user.service.service.user.impl;
import com.menglang.common.library.exceptions.common.BadRequestException;
import com.menglang.common.library.exceptions.common.ConflictException;
import com.menglang.common.library.exceptions.common.NotFoundException;
import com.menglang.user.service.dto.user.UserMapper;
import com.menglang.user.service.dto.user.UserRequest;
import com.menglang.user.service.dto.user.UserResponse;
import com.menglang.user.service.entity.model.User;
import com.menglang.user.service.repository.GroupRepository;
import com.menglang.user.service.repository.RoleRepository;
import com.menglang.user.service.repository.UserRepository;
import com.menglang.user.service.service.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;
    private final UserRepository userRepository;
    private final GroupRepository groupRepository;
    private final RoleRepository roleRepository;

    @Override
    public UserResponse update(Long id, UserRequest userRequest) {
        User updatedUser=this.findUserById(id);
        if(userRepository.existByUsername(userRequest.username(),id))
            throw new ConflictException("User already exist");
        userMapper.updateUser(userRequest,updatedUser,roleRepository,groupRepository);
        try{
            return userMapper.toResponse(userRepository.save(updatedUser));
        }catch (RuntimeException e){
            log.warn("Cannot Update User {}",e.getMessage());
            throw new BadRequestException("Failed to Update User");
        }
    }

    @Override
    public UserResponse findById(Long id) {
        return userMapper.toResponse(this.findUserById(id));
    }

    @Override
    public UserResponse findByUsername(String username) {
        Optional<User> user=userRepository.findByUsername(username);
        if (user.isPresent()) return userMapper.toResponse(user.get());
        else throw new NotFoundException("Username not Found!");
    }

    private User findUserById(Long id){
        return userRepository.findById(id).orElseThrow(()->new NotFoundException("User Not Found!"));
    }

}
