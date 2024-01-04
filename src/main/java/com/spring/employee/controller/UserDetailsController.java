package com.spring.employee.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.spring.employee.dto.BankDto;
import com.spring.employee.dto.EducationDto;
import com.spring.employee.dto.PersonalDto;
import com.spring.employee.model.User;
import com.spring.employee.repository.EducationRepository;
import com.spring.employee.repository.PersonalRepository;
import com.spring.employee.repository.UserRepository;
import com.spring.employee.service.UserDetailsService;
import com.spring.employee.service.impl.UserDetailsServiceImpl;

@Controller
public class UserDetailsController {
    
    UserDetailsServiceImpl userDetailsServiceImpl;
    
    @Autowired
    UserDetailsService userDetailsService;

    @Autowired
    PersonalRepository personalRepository;

    @Autowired
    EducationRepository educationRepository;

    @Autowired
    UserRepository userRepository;


    public UserDetailsController(UserDetailsServiceImpl userDetailsServiceImpl) {
        this.userDetailsServiceImpl = userDetailsServiceImpl;
    }
    
    @GetMapping("/users/profile/personal")
	public String userPersonal(Model model,Principal principal) {
        User currentuser=userRepository.findByEmail(principal.getName());
        PersonalDto personalDto=userDetailsService.getPersonalDto(currentuser);
        model.addAttribute("personalDto", personalDto);
		return "personal";
	}

    @PostMapping("/users/profile/personal/save")
    public String submitPersonal(@ModelAttribute PersonalDto personalDto,Principal principal){
        String output="";
        output=userDetailsServiceImpl.savePersonal(personalDto,principal);
        if (output.equals("success")) {
			return "redirect:/users?success";
		}
        return "redirect:/users?error";
    }

    @GetMapping("/users/profile/education")
	public String userEducation(Model model,Principal principal) {
        User currentuser=userRepository.findByEmail(principal.getName());
        EducationDto educationDto=userDetailsService.getEducationDto(currentuser);
        model.addAttribute("educationDto", educationDto);
		return "education";
	}

    @PostMapping("/users/profile/education/save")
    public String submitEducation(@ModelAttribute EducationDto educationDto,Principal principal){
        String output="";
        output=userDetailsServiceImpl.saveEducation(educationDto,principal);
        if (output.equals("success")) {
			return "redirect:/users?success";
		}
        return "redirect:/users?error";
    }

    @GetMapping("/users/profile/bank")
	public String userBank(Model model,Principal principal) {
        User currentuser=userRepository.findByEmail(principal.getName());
        BankDto bankDto=userDetailsService.getBankDto(currentuser);
        model.addAttribute("bankDto", bankDto);
		return "bank";
	}

    @PostMapping("/users/profile/bank/save")
    public String submitBank(@ModelAttribute BankDto bankDto,Principal principal){
        String output="";
        output=userDetailsServiceImpl.saveBank(bankDto,principal);
        if (output.equals("success")) {
			return "redirect:/users?success";
		}
        return "redirect:/users?error";
    }
}
