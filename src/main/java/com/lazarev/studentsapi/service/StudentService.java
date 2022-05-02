package com.lazarev.studentsapi.service;

import com.lazarev.studentsapi.model.Student;

import java.util.List;
import java.util.Optional;

public interface StudentService {

    List<Student> findAllStudents();
    Optional<Student> findStudentById(String id);
    Optional<Student> findStudentByEmail(String email);

    void insertStudent(Student student);

    void updateStudent(String id, Student student);

    void deleteStudent(String id);

    Optional<Student> findStudentByFirstnameAndLastname(String firstname, String lastname);
}
