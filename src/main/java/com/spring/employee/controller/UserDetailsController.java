package com.spring.employee.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.spring.employee.dto.BankDto;
import com.spring.employee.dto.DescriptionDto;
import com.spring.employee.dto.EducationDto;
import com.spring.employee.dto.PersonalDto;
import com.spring.employee.model.Description;
import com.spring.employee.model.User;
import com.spring.employee.repository.DescriptionRepository;
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
    DescriptionRepository descriptionRepository;

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

    //Get all user project list here//

    // @GetMapping("/home")
    // public String listRegisteredUsers(Model model){
    //     // String currentUserEmail=principal.getName();
    //     // User currentUser=userRepository.findByEmail(currentUserEmail);
    //     List<DescriptionDto> descriptionDto=userDetailsService.findAllUsers();
    //     //User currentUser = userRepository.findByEmail(principal.getName());
    //     //DescriptionDto descriptionDto=userDetailsService.findAllUsers(currentUser);
    //     //List<DescriptionDto> users = userDetailsService.findAllUsers();
    //     model.addAttribute("users", descriptionDto);
    //     return "home";
    // }

    @GetMapping("/home")
    public String listByUserId(Model model,Principal principal){
        try{
            List<Description> descriptions=new ArrayList<>();
            User currentUser = userRepository.findByEmail(principal.getName());
            descriptionRepository.findByUserId(currentUser.getId()).forEach(descriptions::add);
            model.addAttribute("users",descriptions);
        }catch(Exception e){
            model.addAttribute("message",e.getMessage());
        }
        return "home";
    }

    /* @GetMapping("/users")
    public String listRegisteredUsers(Model model){
        List<UserDto> users = userService.findAllUsers();
        model.addAttribute("users", users);
        return "users";
    } */

    @GetMapping("/users/profile/desc")
	public String descDetails(Model model) {
        DescriptionDto descriptionDto=new DescriptionDto();
         model.addAttribute("descDto", descriptionDto);
		return "addtitle";
	}

    @GetMapping("/users/profile/edit/{id}")
	public String descEdit(@PathVariable(value = "id")Long id, Model model,RedirectAttributes redirectAttributes) {
        //Description descriptionDto=userDetailsServiceImpl.getTaskById(taskId);
        try{
            Description description=descriptionRepository.findById(id).get();
            model.addAttribute("description",description);
            return "editpage";
        }catch(Exception e){
            redirectAttributes.addFlashAttribute("message", e.getMessage());
            return "redirect:/home";
        }
	}

    @GetMapping("/users/profile/view/{id}")
	public String descView(@PathVariable(value = "id")Long id, Model model,RedirectAttributes redirectAttributes) {
        //Description descriptionDto=userDetailsServiceImpl.getTaskById(taskId);
        try{
            Description description=descriptionRepository.findById(id).get();
            model.addAttribute("description",description);
            return "viewtitle";
        }catch(Exception e){
            redirectAttributes.addFlashAttribute("message", e.getMessage());
            return "redirect:/home";
        }
	}


    /*@GetMapping("/forgotPassword")
	public String forgotPassword() {s
		return "forgotPassword";
	} */

    @PostMapping("/users/profile/descsave")
    public String submitDesc(@ModelAttribute DescriptionDto descriptionDto,Principal principal){
        String output="";
        output=userDetailsServiceImpl.saveDesc(descriptionDto,principal);
        if (output.equals("success")) {
			return "redirect:/home?success";
		}
        return "redirect:/home?error";
    }


    @GetMapping("/users/profile/delete/{id}")
    public String deleteTutorial(@PathVariable(value = "id") Long id, Model model, RedirectAttributes redirectAttributes) {
    try {
      this.descriptionRepository.deleteById(id);

      redirectAttributes.addFlashAttribute("message", "The Tutorial with id=" + id + " has been deleted successfully!");
    } catch (Exception e) {
      redirectAttributes.addFlashAttribute("message", e.getMessage());
    }

    return "redirect:/home";
  }


}
