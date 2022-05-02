package com.lazarev.studentsapi.repository;

import com.lazarev.studentsapi.model.Student;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.Optional;

public interface StudentRepository extends MongoRepository<Student, String> {

    Optional<Student> findByEmail(String email);

    @Query("{firstname: ?0, lastname:  ?1}")
    Optional<Student> findByFirstnameAndLastname(String firstname, String lastname);

}
