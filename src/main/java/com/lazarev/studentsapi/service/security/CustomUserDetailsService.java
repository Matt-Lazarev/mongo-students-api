package com.lazarev.studentsapi.service.security;

import com.lazarev.studentsapi.exception.custom.UserNotFoundException;
import com.lazarev.studentsapi.model.security.ApplicationUser;
import com.lazarev.studentsapi.repository.security.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username)  {
        List<ApplicationUser> all = userRepository.findAll();
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException(
                        "User with username = '%s' not found".formatted(username)));
    }
}
