package com.lazarev.studentsapi.repository.security;

import com.lazarev.studentsapi.model.security.ApplicationUser;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserRepository extends MongoRepository<ApplicationUser, String> {

    Optional<ApplicationUser> findByUsername(String username);
}
