package com.spring.employee.dto;

import com.spring.employee.model.EducationDetails;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EducationDto {
    private String hscMark;
    private String sslcMark;
    private String institution;
    private String branch;
    private String cgpa;

    public void setAll(EducationDetails educationDetails){
        this.setSslcMark(educationDetails.getSslcMark());
        this.setBranch(educationDetails.getBranch());
        this.setHscMark(educationDetails.getHscMark());
        this.setCgpa(educationDetails.getCgpa());
        this.setInstitution(educationDetails.getInstitution());
    }
}
