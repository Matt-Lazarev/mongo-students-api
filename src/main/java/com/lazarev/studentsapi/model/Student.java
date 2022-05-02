package com.lazarev.studentsapi.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor

@Schema(description = "Student document")
@Document("students")
public class Student {

    @Id
    private String id;

    @NotNull(message = "Student's firstname cannot be null")
    @NotBlank(message = "Student's firstname cannot be blank")
    private String firstname;

    @NotNull(message = "Student's lastname cannot be null")
    @NotBlank(message = "Student's lastname cannot be blank")
    private String lastname;

    @Indexed(unique = true)
    @NotNull(message = "Student's email cannot be null")
    @NotBlank(message = "Student's email cannot be blank")
    @Email(message = "Incorrect email format")
    private String email;

    @NotNull(message = "Student's gender cannot be null")
    private Gender gender;

    private Address address;
    private Set<String> favouriteSubjects;
    private LocalDateTime created;
}
