package com.menglang.user.service.service.refreshToken;

import com.menglang.common.library.exceptions.common.BadRequestException;
import com.menglang.user.service.constant.ApiConstant;
import com.menglang.user.service.dto.ResponseTemplate;
import com.menglang.user.service.dto.authenticate.AuthenticateResponse;
import com.menglang.user.service.dto.requestToken.RefreshTokenReq;
import com.menglang.user.service.entity.model.RefreshToken;
import com.menglang.user.service.entity.model.User;
import com.menglang.user.service.entity.model.UserPrincipal;
import com.menglang.user.service.exception.BaseException;
import com.menglang.user.service.repository.RefreshTokenRepository;
import com.menglang.user.service.repository.UserRepository;
import com.menglang.user.service.service.auth.impl.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class RefreshTokenServiceImpl implements RefreshTokenService {
    private final RefreshTokenRepository refreshTokenRepository;
    private final CustomUserDetailsService customUserDetailsService;
    private final UserRepository userRepository;

    @Override
    public void createRefreshToken(String username, String token, Date tokenExpireAt) {
        Optional<User> user = userRepository.findFirstByUsernameAndStatus(username, ApiConstant.ACTIVE.getKey());
        if (user.isEmpty()) {
            throw new BaseException(
                    ApiConstant.USER_NAME_NOT_FOUND.getDescription(),
                    ApiConstant.USER_NAME_NOT_FOUND.getKey(),
                    null,
                    HttpStatus.NOT_FOUND);
        }

        RefreshToken refreshToken;
        Optional<RefreshToken> refreshTokenOptional = refreshTokenRepository.findByUser(user.get());
        if (refreshTokenOptional.isPresent()) {
            log.info("Refresh Token already exist.");
            refreshToken = refreshTokenOptional.get();
            refreshToken.setToken(token);
            refreshToken.setExpiryDate(tokenExpireAt);
        } else {
            refreshToken = RefreshToken.builder()
                    .token(token)
                    .expiryDate(tokenExpireAt)
                    .user(user.get())
                    .build();
        }

        log.info("Refresh Token Created Successful.");
        refreshTokenRepository.save(refreshToken);
    }

    @Override
    public ResponseTemplate refreshToken(RefreshTokenReq refreshTokenReq) {
        try {
            ResponseTemplate responseTemplate = findByToken(refreshTokenReq.refreshToken())
                    .map(this::verifyExpireToken)
                    .map(RefreshToken::getUser)
                    .map(userInfo -> {
                        final UserPrincipal userPrincipal = customUserDetailsService.customUserDetails(userInfo.getUsername());
                        final String accessToken = "SSS";
                        var responseToken = new AuthenticateResponse(accessToken, refreshTokenReq.refreshToken());
                        return new ResponseTemplate(
                                LocalDateTime.now(),
                                ApiConstant.REFRESH_TOKEN_SUCCESS.getDescription(),
                                ApiConstant.REFRESH_TOKEN_SUCCESS.getKey(),
                                false,
                                responseToken
                        );

                    }).orElseThrow();
               return responseTemplate;
        } catch (RuntimeException e) {
            log.error("Error while refreshing token with request: {}", refreshTokenReq, e);
            throw new BaseException(
                    ApiConstant.UN_AUTHORIZATION.getDescription(),
                    ApiConstant.UN_AUTHORIZATION.getKey(),
                    null,
                    HttpStatus.UNAUTHORIZED
            );
        }

    }

    @Override
    public Optional<RefreshToken> findByToken(String token) {
        return refreshTokenRepository.findByToken(token);
    }

    @Override
    public RefreshToken verifyExpireToken(RefreshToken token) {
        if (token.getExpiryDate().compareTo(Date.from(Instant.now())) < 0) {
            refreshTokenRepository.delete(token);
            log.info("Refresh refreshToken is expired. Please make a new login..!");
            throw new BadRequestException(token.getToken() + " Refresh refreshToken is expired. Please make a new login..!");
        }
        return token;
    }

    @Override
    public void deleteToken(String token) {
        refreshTokenRepository.deleteByToken(token);
    }

    @Override
    public void deleteAllTokenByUserIds(List<User> userList) {
        refreshTokenRepository.deleteAllByUserIn(userList);
    }
}
