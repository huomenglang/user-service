package com.menglang.user.service.service.user;

import com.menglang.user.service.dto.user.UserRequest;
import com.menglang.user.service.dto.user.UserResponse;

public interface UserService {

    UserResponse update(Long id, UserRequest userRequest);

    UserResponse findById(Long id);

//    @Transactional(readOnly = true)
//    UserResponse findAll(UserFilterRequest filterRequest);

    UserResponse findByUsername(String username);

}
