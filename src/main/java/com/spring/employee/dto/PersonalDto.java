package com.spring.employee.dto;

import java.time.LocalDate;

import com.spring.employee.model.PersonalDetails;

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

    public void setAll(PersonalDetails personalDetails) {
        this.setAddress(personalDetails.getAddress());
        this.setBloodGroup(personalDetails.getBloodGroup());
        this.setContactNumber(personalDetails.getContactNumber());
        this.setDomain(personalDetails.getDomain());
        this.setEmail(personalDetails.getEmail());
        this.setDateOfBirth(personalDetails.getDateOfBirth());
        this.setFatherName(personalDetails.getFatherName());
        this.setMotherName(personalDetails.getMotherName());
        this.setName(personalDetails.getName());
    }
}
