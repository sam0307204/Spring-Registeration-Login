package com.spring.employee.dto;

import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PersonalDto {
    private String name;
    private LocalDate dateOfBirth;
    private String fatherName;
    private String motherName;
    private String email;
    private String domain;
    private String bloodGroup;
    private String address;
    private String contactNumber;
}
