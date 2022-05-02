package com.lazarev.studentsapi.config.security.jwt;

import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Date;

@Slf4j
@Component
public class JwtUtils {
    private static String JWT_SECRET;
    private static int JWT_ACCESS_TOKEN_EXPIRATION_MS;
    private static int JWT_REFRESH_TOKEN_EXPIRATION_MS;

    //Fields to inject static members with @Value
    private String jwtSecret;
    private int jwtAccessTokenExpirationMs;
    private int jwtRefreshTokenExpirationMs;


    @Autowired
    public void setJwtSecret(@Value("${jwt-config.jwtSecret}") String jwtSecret) {
        JWT_SECRET = jwtSecret;
    }

    @Autowired
    public void setJwtAccessTokenExpirationMs(@Value("${jwt-config.jwtAccessTokenExpirationMs}") int jwtAccessTokenExpirationMs) {
        JWT_ACCESS_TOKEN_EXPIRATION_MS = jwtAccessTokenExpirationMs;
    }

    @Autowired
    public void setJwtRefreshTokenExpirationMs(@Value("${jwt-config.jwtRefreshTokenExpirationMs}") int jwtRefreshTokenExpirationMs) {
        JWT_REFRESH_TOKEN_EXPIRATION_MS = jwtRefreshTokenExpirationMs;
    }

    public static String generateAccessToken(String username, Collection<? extends GrantedAuthority> authorities) {
        return buildToken(username, authorities)
                .setExpiration(new Date((new Date()).getTime() + JWT_ACCESS_TOKEN_EXPIRATION_MS))
                .compact();
    }

    public static String generateRefreshToken(String username, Collection<? extends GrantedAuthority> authorities) {
        return buildToken(username, authorities)
                .setExpiration(new Date((new Date()).getTime() + JWT_REFRESH_TOKEN_EXPIRATION_MS))
                .compact();
    }

    private static JwtBuilder buildToken(String username, Collection<? extends GrantedAuthority> authorities){
        return Jwts.builder()
                .setSubject(username)
                .claim("authorities", authorities)
                .setIssuedAt(new Date())
                .signWith(SignatureAlgorithm.HS512, JWT_SECRET);
    }

    public static Claims parseJwtToken(String authToken) {
        if(authToken.startsWith("Bearer ")){
            authToken = authToken.replace("Bearer ", "");
        }

        Jws<Claims> claimsJws = Jwts.parser()
                .setSigningKey(JWT_SECRET)
                .parseClaimsJws(authToken);

        return claimsJws.getBody();
    }
}