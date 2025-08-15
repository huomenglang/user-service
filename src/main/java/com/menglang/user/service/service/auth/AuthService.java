package com.menglang.user.service.service.auth;

import com.menglang.user.service.dto.ResponseTemplate;
import com.menglang.user.service.dto.authenticate.AuthenticationRequest;
import com.menglang.user.service.dto.authenticate.UserChangePasswordRequest;
import com.menglang.user.service.dto.user.UserRequest;
import org.springframework.security.core.Authentication;

public interface AuthService {
    ResponseTemplate register(UserRequest userRequest);
    ResponseTemplate authenticate(AuthenticationRequest authRequest);
    ResponseTemplate resetPassword (UserChangePasswordRequest resetPasswordDTO);
    ResponseTemplate getMe(Authentication authentication);
}
