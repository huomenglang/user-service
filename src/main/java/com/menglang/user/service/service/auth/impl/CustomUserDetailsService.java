package com.menglang.user.service.service.auth.impl;

import com.menglang.user.service.constant.ApiConstant;
import com.menglang.user.service.entity.model.Role;
import com.menglang.user.service.entity.model.User;
import com.menglang.user.service.entity.model.UserPrincipal;
import com.menglang.user.service.exception.BaseException;
import com.menglang.user.service.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
      return this.customUserDetails(username);
    }

    public UserPrincipal customUserDetails(String username){
        Optional<User> user=this.userRepository.findFirstByUsernameAndStatus(username, ApiConstant.IN_ACTIVE.getKey());

        if (user.isEmpty()){
            log.warn("Username {} unauthorized", username);
            throw new BaseException(
                    "Unauthorized",
                    String.valueOf(HttpStatus.UNAUTHORIZED.value()),
                    null,
                    HttpStatus.UNAUTHORIZED);
        }
       user.ifPresent(
               u->{
                   if(!u.getStatus().equals(ApiConstant.ACTIVE.getKey())){
                       log.warn("Username {} blocked", username);
                       throw new BaseException(
                               "Blocked",
                               String.valueOf(HttpStatus.FORBIDDEN.value()),
                               null,
                               HttpStatus.UNAUTHORIZED);
                   }
                   if (u.getLoginAttempt() > u.getMaxAttempt()) {
                       log.warn("Username {} attempt more than 3", username);
                       throw new BaseException(
                               "Unauthorized",
                               String.valueOf(HttpStatus.UNAUTHORIZED.value()),
                               null,
                               HttpStatus.UNAUTHORIZED);
                   }
               }
       );

        return new UserPrincipal(
                user.get().getUsername(),
                user.get().getPassword(),
                this.extractRoleGrantAuthorities(user.get().getRoles())
        );
    }

    public Set<SimpleGrantedAuthority> extractRoleGrantAuthorities(Set<Role> roles) {
        Set<SimpleGrantedAuthority> roleAuthorities=roles.stream().map(r->new SimpleGrantedAuthority("ROLE_"+r.getName())).collect(Collectors.toSet());
        Set<SimpleGrantedAuthority> rolePermissions=roles.stream().flatMap(this::extractPermissions).collect(Collectors.toSet());

        rolePermissions.addAll(roleAuthorities);
        return rolePermissions;
    }
    public Stream<SimpleGrantedAuthority> extractPermissions(Role role){
      return   role.getPermissions().stream().map(per->new SimpleGrantedAuthority(per.getName()));
    }
}
