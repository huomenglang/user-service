package com.menglang.user.service.service.user;

import com.menglang.user.service.dto.authenticate.UserChangePasswordRequest;
import com.menglang.user.service.dto.user.UserResponse;

import java.util.Set;

public interface UserChangeService {
    UserResponse changePassword(Long id, UserChangePasswordRequest userChangePasswordRequest);

    UserResponse disActivateUser(Long id, String status);

    UserResponse resetPassword(Long id);

}
