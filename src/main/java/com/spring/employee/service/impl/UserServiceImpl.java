package com.spring.employee.service.impl;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.spring.employee.dto.UserDetailsDto;
import com.spring.employee.dto.UserDto;
import com.spring.employee.model.User;
import com.spring.employee.model.UserDetails;
import com.spring.employee.model.PasswordResetToken;
import com.spring.employee.model.Role;
import com.spring.employee.repository.RoleRepository;
import com.spring.employee.repository.TokenReposirory;
import com.spring.employee.repository.UserDetailsRepository;
import com.spring.employee.repository.UserRepository;
import com.spring.employee.service.UserService;

@Service
public class UserServiceImpl implements UserService  {


    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private UserDetailsRepository userDetailsRepository;
    private PasswordEncoder passwordEncoder;

    @Autowired
    private  JavaMailSender javaMailSender;

    @Autowired
    TokenReposirory tokenReposirory;

    public UserServiceImpl(UserRepository userRepository,
                           RoleRepository roleRepository,
                           PasswordEncoder passwordEncoder,
                           UserDetailsRepository userDetailsRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.userDetailsRepository=userDetailsRepository;
    }


    @Override
    public void saveUser(UserDto userDto) {
        User user = new User();
        user.setName(userDto.getFirstName() + " " + userDto.getLastName());
        user.setEmail(userDto.getEmail());
        // encrypt the password using spring security
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));

        Role role = roleRepository.findByName("ROLE_ADMIN");
        if(role == null){
            role = checkRoleExist();
        }
        user.setRoles(Arrays.asList(role));
        userRepository.save(user);
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public List<UserDto> findAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map((user) -> mapToUserDto(user))
                .collect(Collectors.toList());
    }


    private UserDto mapToUserDto(User user){
        UserDto userDto = new UserDto();
        String[] str = user.getName().split(" ");
        userDto.setFirstName(str[0]);
        userDto.setLastName(str[1]);
        userDto.setEmail(user.getEmail());
        return userDto;
    }

    private Role checkRoleExist(){
        Role role = new Role();
        role.setName("ROLE_ADMIN");
        return roleRepository.save(role);
    }


    //Send email to the user email address.
    public String sendEmail(User user) {
		try {
			String resetLink = generateResetToken(user);

			SimpleMailMessage msg = new SimpleMailMessage();
			msg.setFrom("kumarnexi2511@gmail.com");// input the senders email ID
			msg.setTo(user.getEmail());

			msg.setSubject("Welcome To My Company");
			msg.setText("Hello \n\n" + "Please click on this link to Reset your Password :" + resetLink + ". \n\n"
					+ "Regards \n" + "Dhanush kumar Developer.");

			javaMailSender.send(msg);

			return "success";
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}

    //Generating Token here.
    public String generateResetToken(User user) {
		UUID uuid = UUID.randomUUID();
		LocalDateTime currentDateTime = LocalDateTime.now();
		LocalDateTime expiryDateTime = currentDateTime.plusMinutes(30);
		PasswordResetToken resetToken = new PasswordResetToken();
		resetToken.setUser(user);
		resetToken.setToken(uuid.toString());
		resetToken.setExpiryDateTime(expiryDateTime);
		resetToken.setUser(user);
		PasswordResetToken token = tokenReposirory.save(resetToken);
		if (token != null) {
			String endpointUrl = "http://localhost:8080/resetPassword";
			return endpointUrl + "/" + resetToken.getToken();
		}
		return "";
	}


    //To check the token is expired or not
    public boolean hasExipred(LocalDateTime expiryDateTime) {
		LocalDateTime currentDateTime = LocalDateTime.now();
		return expiryDateTime.isAfter(currentDateTime);
	}


    public UserDetailsDto getUserDetails(Long userId) {
        UserDetails userDetails=userDetailsRepository.findByUserId(userId);
        UserDetailsDto userDetailsDto=new UserDetailsDto();

        if(userDetails!=null){
            userDetailsDto.setAddress(userDetails.getAddress());
            userDetailsDto.setPhoneNumber(userDetails.getPhoneNumber());
        }
        return userDetailsDto;
    }
}
