package com.lazarev.studentsapi.config.security;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "Request model for obtain JWT Token")
public class UsernamePasswordAuthenticationRequest {
    private String username;
    private String password;
}
