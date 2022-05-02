package com.lazarev.studentsapi.config.security.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lazarev.studentsapi.config.security.UsernamePasswordAuthenticationRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@AllArgsConstructor
@Slf4j
public class JwtUsernamePasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        if (!request.getMethod().equals("POST")) {
            throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
        }

        try{
            var authenticationRequest = new ObjectMapper().readValue(request.getInputStream(), UsernamePasswordAuthenticationRequest.class);

            String username = authenticationRequest.getUsername();
            String password = authenticationRequest.getPassword();

            Authentication authentication = new UsernamePasswordAuthenticationToken(username, password);
            return authenticationManager.authenticate(authentication);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        String accessToken = JwtUtils.generateAccessToken(authResult.getName(), authResult.getAuthorities());
        String refreshToken = JwtUtils.generateRefreshToken(authResult.getName(), authResult.getAuthorities());

        response.addHeader("Authorization", "Bearer %s".formatted(accessToken));

        Map<String, String> tokens = Map.of(
                "access_token", "Bearer %s".formatted(accessToken),
                "refresh_token", "Bearer %s".formatted(refreshToken));

        new ObjectMapper()
                .writerWithDefaultPrettyPrinter()
                .writeValue(response.getWriter(), tokens);
    }
}
