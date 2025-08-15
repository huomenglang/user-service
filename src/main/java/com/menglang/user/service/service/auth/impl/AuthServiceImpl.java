package com.menglang.user.service.service.auth.impl;

import com.menglang.common.library.exceptions.common.BadRequestException;
import com.menglang.common.library.exceptions.common.ConflictException;
import com.menglang.common.library.exceptions.common.NotFoundException;
import com.menglang.common.library.exceptions.common.UnauthorizedException;
import com.menglang.user.service.dto.ResponseTemplate;
import com.menglang.user.service.dto.authenticate.AuthenticateResponse;
import com.menglang.user.service.dto.authenticate.AuthenticationRequest;
import com.menglang.user.service.dto.authenticate.MeResponse;
import com.menglang.user.service.dto.authenticate.UserChangePasswordRequest;
import com.menglang.user.service.dto.user.UserMapper;
import com.menglang.user.service.dto.user.UserRequest;
import com.menglang.user.service.entity.model.*;
import com.menglang.user.service.repository.GroupRepository;
import com.menglang.user.service.repository.RoleRepository;
import com.menglang.user.service.repository.UserRepository;
import com.menglang.user.service.service.auth.AuthService;
import com.menglang.user.service.service.jwt.JwtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final RoleRepository roleRepository;
    private final GroupRepository groupRepository;
    private final PasswordEncoder passwordEncoder;
    private final CustomUserDetailsService userDetailsService;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    @Override
    public ResponseTemplate register(UserRequest userRequest) {

        if(userRepository.existByUsername(userRequest.username(),0L))
            throw new ConflictException("User already exist");
        User user=userMapper.toUser(userRequest,roleRepository,groupRepository);
        user.setPassword(passwordEncoder.encode(userRequest.password()));
        try{
            User savedUser= userRepository.save(user);
            return ResponseTemplate.builder()
                    .code("201")
                    .data(userMapper.toResponse(savedUser))
                    .message("User Register Successful")
                    .dateTime(LocalDateTime.now())
                    .build();

        }catch (RuntimeException e){
            log.warn("Cannot Create User {}",e.getMessage());
            throw new BadRequestException("Failed to create User");
        }

    }

    @Override
    public ResponseTemplate authenticate(AuthenticationRequest authRequest) {
        final String username = authRequest.username();
        final String password = authRequest.password();
        User user = userRepository.findByUsername(username).orElseThrow(() -> new NotFoundException("User Not Found"));
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        username,
                        password
                )
        );

        if (authentication.isAuthenticated()){
           AuthenticateResponse userAuthentication=this.extractAuthentication(user);
            return ResponseTemplate.builder()
                    .code("200")
                    .data(userAuthentication)
                    .message("User Login Successful")
                    .dateTime(LocalDateTime.now())
                    .build();
        }else{
            throw new UnauthorizedException("Invalid Credentials!");
        }

    }

    @Override
    public ResponseTemplate resetPassword(UserChangePasswordRequest resetPasswordDTO) {
        return null;
    }

    @Override
    public ResponseTemplate getMe(Authentication authentication) {
        String username=authentication.getName();
        User user= userRepository.findByUsername(username).orElseThrow(() -> new NotFoundException("User Not Found"));

        Set<String> groups=user.getGroups().stream().map(Group::getName).collect(Collectors.toSet());
        Set<String> roles=user.getRoles().stream().map(Role::getName).collect(Collectors.toSet());
        Set<String> permissions=user.getRoles().stream().flatMap(r->r.getPermissions().stream().map(Permission::getName)).collect(Collectors.toSet());

        MeResponse me=MeResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .username(username)
                .firstname(user.getFirstName())
                .lastname(user.getLastName())
                .roles(roles)
                .groups(groups)
                .permissions(permissions)
                .build();
        return ResponseTemplate.builder()
                .message("success")
                .data(me)
                .code("200")
                .dateTime(LocalDateTime.now())
                .build();
    }

    private AuthenticateResponse extractAuthentication(User user){
        UserPrincipal userPrincipal= userDetailsService.customUserDetails(user.getUsername());
        String accessToken= jwtService.generateToken(userPrincipal);
        String refreshToken=jwtService.refreshToken(userPrincipal);

        return new AuthenticateResponse(
               user.getUsername(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                userPrincipal.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList(),
                accessToken,
                refreshToken



        );

    }
}
