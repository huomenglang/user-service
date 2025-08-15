package com.menglang.user.service.controller.user;

import com.menglang.user.service.dto.authenticate.UserChangePasswordRequest;
import com.menglang.user.service.dto.user.UserRequest;
import com.menglang.user.service.dto.user.UserResponse;
import com.menglang.user.service.service.user.UserChangeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/public/auth")
public class AuthController {
    private final UserChangeService userService;

    @PostMapping("/{id}/password/change")
    public ResponseEntity<UserResponse> disActive(@PathVariable Long id, @Valid @RequestBody UserChangePasswordRequest request  ){
        return ResponseEntity.ok(userService.changePassword(id,request));
    }

    @GetMapping("/{id}/password/reset")
    public ResponseEntity<UserResponse> disActive(@PathVariable Long id){
        return ResponseEntity.ok(userService.resetPassword(id));
    }


}
