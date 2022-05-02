package com.lazarev.studentsapi.controller;

import com.lazarev.studentsapi.exception.custom.StudentNotFoundException;
import com.lazarev.studentsapi.model.Student;
import com.lazarev.studentsapi.service.StudentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/students")
@AllArgsConstructor
@Tag(name = "Student API", description = "operations with Students")
public class StudentController {
    private final StudentService studentService;

    @Operation(summary = "Gets all students")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found Students",
                    content = {@Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = Student.class)))}),
            @ApiResponse(responseCode = "403", description = "No access rights for endpoint"),
            @ApiResponse(responseCode = "404", description = "No one Student found",
                    content = {@Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = Student.class)))})})
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    @GetMapping
    public ResponseEntity<List<Student>> getAllStudents(){
        List<Student> students = studentService.findAllStudents();
        return ResponseEntity
                .status(students.size() > 0 ? HttpStatus.OK : HttpStatus.NOT_FOUND)
                .body(students);
    }

    @Operation(summary = "Gets student by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found student",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Student.class))}),
            @ApiResponse(responseCode = "403", description = "No access rights for endpoint"),
            @ApiResponse(responseCode = "404", description = "Student not found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = StudentNotFoundException.class)))})
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    @GetMapping("/{id}")
    public ResponseEntity<Student> getStudentById(@PathVariable String id){
        Optional<Student> studentOptional = studentService.findStudentById(id);
        return studentOptional
                .map(student -> ResponseEntity
                    .status(HttpStatus.OK)
                    .body(student))
                .orElseGet(() -> ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .build());
    }

    @Operation(summary = "Gets student by email")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found student",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Student.class))}),
            @ApiResponse(responseCode = "403", description = "No access rights for endpoint"),
            @ApiResponse(responseCode = "404", description = "Student not found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = StudentNotFoundException.class)))})
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    @GetMapping("/by-email/{email}")
    public ResponseEntity<Student> getStudentByEmail(@PathVariable String email){
        Optional<Student> studentOptional = studentService.findStudentByEmail(email);
        return studentOptional
                .map(student -> ResponseEntity
                        .status(HttpStatus.OK)
                        .body(student))
                .orElseGet(() -> ResponseEntity
                        .status(HttpStatus.NOT_FOUND)
                        .build());
    }

    @Operation(summary = "Gets student by firstname and lastname")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found student",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Student.class))}),
            @ApiResponse(responseCode = "403", description = "No access rights for endpoint"),
            @ApiResponse(responseCode = "404", description = "Student not found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = StudentNotFoundException.class)))})
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    @GetMapping("/by-name")
    public ResponseEntity<Student> getStudentByFirstnameAndLastname(@RequestParam String firstname,
                                                                   @RequestParam String lastname){
        Optional<Student> studentOptional = studentService
                                    .findStudentByFirstnameAndLastname(firstname, lastname);
        return studentOptional
                .map(student -> ResponseEntity
                        .status(HttpStatus.OK)
                        .body(student))
                .orElseGet(() -> ResponseEntity
                        .status(HttpStatus.NOT_FOUND)
                        .build());
    }

    @Operation(summary = "Saves new student")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Saved student"),
            @ApiResponse(responseCode = "403", description = "No access rights for endpoint"),
    })
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @PostMapping
    public ResponseEntity<?> saveStudent(@Valid @RequestBody Student student){
        studentService.insertStudent(student);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .build();
    }

    @Operation(summary = "Updates existing student")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Updated student"),
            @ApiResponse(responseCode = "403", description = "No access rights for endpoint"),
            @ApiResponse(responseCode = "404", description = "Student not found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = StudentNotFoundException.class)))})
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @PutMapping("{id}")
    public ResponseEntity<?> updateStudent(@PathVariable String id,
                                           @Valid @RequestBody Student student){
        studentService.updateStudent(id, student);
        return ResponseEntity
                .status(HttpStatus.OK)
                .build();
    }

    @Operation(summary = "Deletes existing student")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Deleted student"),
            @ApiResponse(responseCode = "403", description = "No access rights for endpoint"),
    })
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteStudent(@PathVariable String id){
        studentService.deleteStudent(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .build();
    }
}
