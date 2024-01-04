package com.spring.employee.controller;

import java.security.Principal;
import java.util.List;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.spring.employee.dto.UserDetailsDto;
import com.spring.employee.dto.UserDto;
import com.spring.employee.model.PasswordResetToken;
import com.spring.employee.model.User;
import com.spring.employee.model.UserDetails;
import com.spring.employee.repository.TokenReposirory;
import com.spring.employee.repository.UserDetailsRepository;
import com.spring.employee.repository.UserRepository;
import com.spring.employee.service.UserService;
import com.spring.employee.service.impl.UserServiceImpl;

@Controller
public class AuthController {

    private UserService userService;

    @Autowired
    UserServiceImpl userServiceImpl;

    @Autowired
    UserRepository userRepository;

    @Autowired
    TokenReposirory tokenReposirory;

    @Autowired
    UserDetailsRepository userDetailsRepository;

    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("index")
    public String home(){
        return "index";
    }

    @GetMapping("/login")
    public String loginForm() {
        return "login";
    }

    // handler method to handle user registration request
    @GetMapping("register")
    public String showRegistrationForm(Model model){
        UserDto user = new UserDto();
        model.addAttribute("user", user);
        return "register";
    }

    // handler method to handle register user form submit request
    @PostMapping("/register/save")
    public String registration(@Valid @ModelAttribute("user") UserDto user,
                               BindingResult result,
                               Model model){
        User existing = userService.findByEmail(user.getEmail());
        if (existing != null) {
            result.rejectValue("email", null, "There is already an account registered with that email");
        }
        if (result.hasErrors()) {
            model.addAttribute("user", user);
            return "register";
        }
        userService.saveUser(user);
        return "redirect:/register?success";
    }

    @GetMapping("/users")
    public String listRegisteredUsers(Model model){
        List<UserDto> users = userService.findAllUsers();
        model.addAttribute("users", users);
        return "users";
    }

    @GetMapping("/forgotPassword")
	public String forgotPassword() {
		return "forgotPassword";
	}

	@PostMapping("/forgotPassword")
	public String forgotPassordProcess(@ModelAttribute UserDto userDTO) {
		String output = "";
		User user = userRepository.findByEmail(userDTO.getEmail());
		if (user != null) {
			output = userServiceImpl.sendEmail(user);
		}
		if (output.equals("success")) {
			return "redirect:/forgotPassword?success";
		}
		return "redirect:/login?error";
	}

	@GetMapping("/resetPassword/{token}")
	public String resetPasswordForm(@PathVariable String token, Model model) {
		PasswordResetToken reset = tokenReposirory.findByToken(token);
		if (reset != null && userServiceImpl.hasExipred(reset.getExpiryDateTime())) {
			model.addAttribute("email", reset.getUser().getEmail());
			return "resetPassword";
		}
		return "redirect:/forgotPassword?error";
	}
	
	@PostMapping("/resetPassword")
	public String passwordResetProcess(@ModelAttribute UserDto userDTO) {
		User user = userRepository.findByEmail(userDTO.getEmail());
		if(user != null) {
			user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
			userRepository.save(user);
		}
		return "redirect:/login";
	}

    @GetMapping("/users/profile")
	public String  userDetails(Model model,Principal principal) {
        User currentUser = userRepository.findByEmail(principal.getName());
        UserDetails userDetails=userDetailsRepository.findByUserId(currentUser.getId());
        UserDetailsDto userDetailsDto=new UserDetailsDto();
        if(userDetails!=null){
            userDetailsDto.setAddress(userDetails.getAddress());
            userDetailsDto.setPhoneNumber(userDetails.getPhoneNumber());
        }
        model.addAttribute("userDetailsDTO", userDetailsDto);
		return "userDetails";
	}

    @PostMapping("/users/profile/save")
    public String submitUserDetails(@ModelAttribute UserDetailsDto userDetailsDTO, Principal principal) {
        UserDetails userDetails = new UserDetails();
        userDetails.setAddress(userDetailsDTO.getAddress());
        userDetails.setPhoneNumber(userDetailsDTO.getPhoneNumber());

        User currentUser = userRepository.findByEmail(principal.getName());
        userDetails.setUser(currentUser);

        userDetailsRepository.save(userDetails);

        return "redirect:/users";
    }
}
