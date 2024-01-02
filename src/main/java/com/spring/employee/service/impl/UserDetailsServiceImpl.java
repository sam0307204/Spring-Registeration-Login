package com.spring.employee.service.impl;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.employee.dto.EducationDto;
import com.spring.employee.dto.PersonalDto;
import com.spring.employee.model.EducationDetails;
import com.spring.employee.model.PersonalDetails;
import com.spring.employee.model.User;
import com.spring.employee.repository.EducationRepository;
import com.spring.employee.repository.PersonalRepository;
import com.spring.employee.repository.UserRepository;

@Service
public class UserDetailsServiceImpl {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PersonalRepository personalRepository;

    @Autowired
    EducationRepository educationRepository;

    public UserDetailsServiceImpl(UserRepository userRepository,
                           PersonalRepository personalRepository,
                           EducationRepository educationRepository) {
        this.userRepository = userRepository;
        this.personalRepository = personalRepository;
        this.educationRepository=educationRepository;
    }

    public String savePersonal(PersonalDto personalDto, Principal principal) {
        PersonalDetails personalDetails=new PersonalDetails();
        personalDetails.setName(personalDto.getName());
        personalDetails.setAddress(personalDto.getAddress());
        personalDetails.setBloodGroup(personalDto.getBloodGroup());
        personalDetails.setContactNumber(personalDto.getContactNumber());
        personalDetails.setDomain(personalDto.getDomain());
        personalDetails.setDateOfBirth(personalDto.getDateOfBirth());
        personalDetails.setEmail(personalDto.getEmail());
        personalDetails.setFatherName(personalDto.getFatherName());
        personalDetails.setMotherName(personalDto.getMotherName());

        User currentuser=userRepository.findByEmail(principal.getName());
        personalDetails.setUser(currentuser);

        personalRepository.save(personalDetails);

        return "success";
    }

    public String saveEducation(EducationDto educationDto, Principal principal) {
        EducationDetails educationDetails=new EducationDetails();
        educationDetails.setInstitution(educationDto.getInstitution());
        educationDetails.setBranch(educationDto.getBranch());
        educationDetails.setCgpa(educationDto.getCgpa());
        educationDetails.setSslcMark(educationDto.getSslcMark());
        educationDetails.setHscMark(educationDto.getHscMark());

        User currentuser=userRepository.findByEmail(principal.getName());
        educationDetails.setUser(currentuser);

        educationRepository.save(educationDetails);
        return "success";
    }
    
}
