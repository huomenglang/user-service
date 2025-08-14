package com.menglang.user.service.service.refreshToken;

import com.menglang.user.service.dto.ResponseTemplate;
import com.menglang.user.service.dto.requestToken.RefreshTokenReq;
import com.menglang.user.service.entity.model.RefreshToken;
import com.menglang.user.service.entity.model.User;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface RefreshTokenService {
    void createRefreshToken(String username, String token, Date tokenExpireAt);
    ResponseTemplate refreshToken(RefreshTokenReq refreshTokenReq);
    Optional<RefreshToken> findByToken(String token);
    RefreshToken verifyExpireToken(RefreshToken refreshTokenReq);
    void deleteToken(String token);
    void deleteAllTokenByUserIds(List<User> userList);
}
