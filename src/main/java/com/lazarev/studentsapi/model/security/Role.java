package com.lazarev.studentsapi.model.security;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

@Getter
@Setter
@NoArgsConstructor

@Schema(description = "Application User's roles")
public class Role implements GrantedAuthority {

    private String name;

    @Override
    public String getAuthority() {
        return "ROLE_" + name;
    }
}
