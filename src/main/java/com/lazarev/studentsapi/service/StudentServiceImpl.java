package com.lazarev.studentsapi.service;

import com.lazarev.studentsapi.exception.custom.StudentNotFoundException;
import com.lazarev.studentsapi.model.Student;
import com.lazarev.studentsapi.repository.StudentRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class StudentServiceImpl implements StudentService{

    private final StudentRepository studentRepository;
    @Override
    @Transactional(readOnly = true)
    public List<Student> findAllStudents() {
        return studentRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Student> findStudentById(String id) {
        return studentRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Student> findStudentByEmail(String email) {
        return studentRepository.findByEmail(email);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Student> findStudentByFirstnameAndLastname(String firstname, String lastname) {
        return studentRepository.findByFirstnameAndLastname(firstname, lastname);
    }

    @Override
    @Transactional
    public void insertStudent(Student student) {
        if(student.getCreated() == null){
            student.setCreated(LocalDateTime.now());
        }
        studentRepository.insert(student);
    }

    @Override
    @Transactional
    public void updateStudent(String id, Student student) {
        Student retrievedStudent = studentRepository.findById(id)
                .orElseThrow(() -> new StudentNotFoundException(
                        String.format("Student with id = '%s' not found", id)));

        retrievedStudent.setFirstname(student.getFirstname());
        retrievedStudent.setLastname(student.getLastname());
        retrievedStudent.setEmail(student.getEmail());
        retrievedStudent.setGender(student.getGender());
        retrievedStudent.setAddress(student.getAddress());
        retrievedStudent.setFavouriteSubjects(student.getFavouriteSubjects());

        studentRepository.save(retrievedStudent);
    }

    @Override
    @Transactional
    public void deleteStudent(String id) {
        studentRepository.deleteById(id);
    }
}
