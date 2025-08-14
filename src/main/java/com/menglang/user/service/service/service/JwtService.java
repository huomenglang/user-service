package com.menglang.user.service.service.service;

import com.menglang.user.service.entity.model.UserPrincipal;
import io.jsonwebtoken.Claims;

import java.security.Key;

public interface JwtService {
    Claims extractClaims(String token);
    Key getKey();
    String generateToken(UserPrincipal userPrincipal);
    String refreshToken(UserPrincipal userPrincipal);
    boolean tokenIsValid(String token);
}
