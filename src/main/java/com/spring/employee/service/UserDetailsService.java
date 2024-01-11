package com.spring.employee.service;

import java.util.List;

import com.spring.employee.dto.BankDto;
import com.spring.employee.dto.DescriptionDto;
import com.spring.employee.dto.EducationDto;
import com.spring.employee.dto.PersonalDto;
import com.spring.employee.model.User;

public interface UserDetailsService {

    PersonalDto getPersonalDto(User user);
    EducationDto getEducationDto(User user);
    BankDto getBankDto(User user);
    List<DescriptionDto> findAllUsers();
        
}
