package com.lazarev.studentsapi.config.security.controller;

import com.lazarev.studentsapi.config.security.jwt.JwtUtils;
import io.jsonwebtoken.Claims;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@Tag(name = "access-token-prolongation-controller", description = "API to prolong the Access Token with Refresh Token")
public class RefreshTokenController {

    @Operation(summary = "Generates new Access Token with Refresh Token")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Updated student"),
            @ApiResponse(responseCode = "404", description = "Wrong Refresh Token")})
    @GetMapping("/api/refresh")
    @SuppressWarnings("unchecked")
    public ResponseEntity<Map<String, String>> refreshToken(HttpServletRequest request) {
        String refreshToken = request.getHeader("Authorization");
        if (refreshToken == null || !refreshToken.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", "invalid Refresh Token"));
        }

        Claims claims = JwtUtils.parseJwtToken(refreshToken);
        String accessToken = JwtUtils.generateAccessToken(
                claims.getSubject(), obtainAuthorities(claims.get("authorities", List.class)));

        Map<String, String> tokens = Map.of(
                "access_token", "Bearer %s".formatted(accessToken),
                "refresh_token", "Bearer %s".formatted(refreshToken));

        return ResponseEntity.status(HttpStatus.OK).body(tokens);
    }

    private Set<? extends GrantedAuthority> obtainAuthorities(List<Map<String, String>> authorities) {
        return authorities
                .stream()
                .map(x -> new SimpleGrantedAuthority(x.get("authority")))
                .collect(Collectors.toSet());
    }
}
