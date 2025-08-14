package com.menglang.user.service.service.jwt;

import com.menglang.common.library.exceptions.common.BadRequestException;
import com.menglang.user.service.configs.properties.JwtConfigProperties;
import com.menglang.user.service.entity.model.UserPrincipal;
import com.menglang.user.service.jwt.JwtSecret;
import com.menglang.user.service.service.auth.CustomUserDetailsService;
import com.menglang.user.service.service.refreshToken.RefreshTokenService;
import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.time.Instant;
import java.util.Date;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class JwtServiceImpl implements JwtService {
    private final JwtConfigProperties jwtConfigs;
    private final JwtSecret jwtSecret;
    private final CustomUserDetailsService userDetailsService;
    private final RefreshTokenService refreshTokenService;

    @Override
    public Claims extractClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    @Override
    public Key getKey() {
        return jwtSecret.getSecretKey(jwtConfigs.getSecret());
    }

    @Override
    public String generateToken(UserPrincipal userPrincipal) {
//        List<String> authorities= new ArrayList<>();
//
//        userPrincipal.getAuthorities().forEach(auth->{
//            authorities.add(auth.getAuthority());
//        });
        Instant currentTime=Instant.now();
        return Jwts
                .builder()
                .setSubject(userPrincipal.getUsername())
                .claim("authorities",userPrincipal.getAuthorities().stream()
                        .map(GrantedAuthority::getAuthority).toList()
                )
                .claim("enabled",userPrincipal.isEnabled())
                .setIssuedAt(Date.from(currentTime))
                .setExpiration(Date.from(currentTime.plusSeconds(jwtConfigs.getExpire())))
                .signWith(getKey(),SignatureAlgorithm.HS256)
                .compact();
    }

    @Override
    public String refreshToken(UserPrincipal userPrincipal) {
        Instant currentTime=Instant.now();
        var tokenExpireAt=Date.from(currentTime.plusSeconds(60000));
        var refreshToken=Jwts.builder()
                .setSubject(userPrincipal.getUsername())
                .setIssuedAt(Date.from(currentTime))
                .setExpiration(tokenExpireAt)
                .signWith(getKey(),SignatureAlgorithm.HS256)
                .compact();
        refreshTokenService.createRefreshToken(userPrincipal.getUsername(),refreshToken,tokenExpireAt);
        return refreshToken;
    }

    @Override
    public boolean tokenIsValid(String token) {
        String username=this.extractUsername(token);
        UserDetails userDetails=userDetailsService.loadUserByUsername(username);
        return userDetails!=null;
    }

    private String extractUsername(String token){
        return this.extractClaimsTFunction(token,Claims::getSubject);
    }

    private <T> T extractClaimsTFunction(String token, Function<Claims,T> claimsTFunction){
        final Claims claims=this.extractAllClaims(token);
        return claimsTFunction.apply(claims);
    }

    private Claims extractAllClaims(String token){
        try {
            return extractClaims(token);
        } catch (ExpiredJwtException ex) {
            throw new BadRequestException("Token was expired.");
        } catch (UnsupportedJwtException ex) {
            throw new BadRequestException("Token is not support");
        } catch (MalformedJwtException ex) {
            throw new BadRequestException("Token is Invalid");
        } catch (Exception ex) {
            throw new BadRequestException(ex.getMessage());
        }
    }
}
