package com.menglang.user.service.service.user.impl;

import com.menglang.common.library.exceptions.common.BadRequestException;
import com.menglang.common.library.exceptions.common.NotFoundException;
import com.menglang.user.service.configs.properties.JwtConfigProperties;
import com.menglang.user.service.dto.authenticate.UserChangePasswordRequest;
import com.menglang.user.service.dto.user.UserMapper;
import com.menglang.user.service.dto.user.UserResponse;
import com.menglang.user.service.entity.model.User;
import com.menglang.user.service.repository.UserRepository;
import com.menglang.user.service.service.user.UserChangeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserChangeServiceImpl implements UserChangeService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtConfigProperties jwtConfigProperties;

    @Override
    public UserResponse changePassword(Long id, UserChangePasswordRequest userChangePasswordRequest) {
        User user=this.findUserById(id);
        String newPwd= userChangePasswordRequest.newPassword();
        String curPwd=userChangePasswordRequest.password();
        String confirmPwd=userChangePasswordRequest.confirmPassword();
        if(!passwordEncoder.matches(curPwd,user.getPassword()))
            throw new BadRequestException("Current Password Don't Matched!");
        if(!newPwd.equals(confirmPwd))
            throw new BadRequestException("New Password and Confirm Password Don't Matched!");
        String encodeNewPwd=passwordEncoder.encode(newPwd);
        user.setPassword(encodeNewPwd);
        try{
            return userMapper.toResponse(userRepository.save(user));
        }catch (RuntimeException e){
            log.warn("Unable to Change Password {}",e.getMessage());
            throw new BadRequestException("Unable to Change Password");
        }

    }

    @Override
    public UserResponse disActivateUser(Long id, String status) {
        User user=this.findUserById(id);
        user.setStatus(status);
        try{
            return userMapper.toResponse(userRepository.save(user));
        }catch (RuntimeException e){
            log.warn("Unable to DisActive Status {}",e.getMessage());
            throw new BadRequestException("Unable to DisActive Status!");
        }
    }

    @Override
    public UserResponse resetPassword(Long id) {
        User user=this.findUserById(id);
        user.setPassword(passwordEncoder.encode(jwtConfigProperties.getDefaultPassword()));
        try{
            return userMapper.toResponse(userRepository.save(user));
        }catch (RuntimeException e){
            log.warn("Unable to reset Status {}",e.getMessage());
            throw new BadRequestException("Unable to Reset Status!");
        }

    }

    private User findUserById(Long id){
        return userRepository.findById(id).orElseThrow(()->new NotFoundException("User Not Found!"));
    }
}
