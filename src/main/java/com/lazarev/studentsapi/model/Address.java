package com.lazarev.studentsapi.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Schema(description = "Student's address")
public class Address {
    private String country;
    private String city;
    private String street;
}
