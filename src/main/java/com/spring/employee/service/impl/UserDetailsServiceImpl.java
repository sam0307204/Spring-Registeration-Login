package com.spring.employee.service.impl;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.employee.dto.BankDto;
import com.spring.employee.dto.DescriptionDto;
import com.spring.employee.dto.EducationDto;
import com.spring.employee.dto.PersonalDto;
import com.spring.employee.model.BankDetails;
import com.spring.employee.model.Description;
import com.spring.employee.model.EducationDetails;
import com.spring.employee.model.PersonalDetails;
import com.spring.employee.model.User;
import com.spring.employee.repository.BankRepository;
import com.spring.employee.repository.DescriptionRepository;
import com.spring.employee.repository.EducationRepository;
import com.spring.employee.repository.PersonalRepository;
import com.spring.employee.repository.UserRepository;
import com.spring.employee.service.UserDetailsService;

@Service
public class UserDetailsServiceImpl implements UserDetailsService{

    @Autowired
    UserRepository userRepository;

    @Autowired
    PersonalRepository personalRepository;

    @Autowired
    EducationRepository educationRepository;

    @Autowired
    BankRepository bankRepository;

    @Autowired
    DescriptionRepository descriptionRepository;

    public UserDetailsServiceImpl(UserRepository userRepository,
                           PersonalRepository personalRepository,
                           EducationRepository educationRepository,
                           DescriptionRepository descriptionRepository) {
        this.userRepository = userRepository;
        this.personalRepository = personalRepository;
        this.educationRepository=educationRepository;
        this.descriptionRepository=descriptionRepository;
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

    public String saveBank(BankDto bankDto, Principal principal) {
        BankDetails bankDetails= new BankDetails();
        bankDetails.setAccountNumber(bankDto.getAccountNumber());
        bankDetails.setBankName(bankDto.getBankName());
        bankDetails.setBranchName(bankDto.getBranchName());
        bankDetails.setEmail(bankDto.getEmail());
        bankDetails.setIfscCode(bankDto.getIfscCode());
        bankDetails.setPhoneNumber(bankDto.getPhoneNumber());

        User currentuser=userRepository.findByEmail(principal.getName());
        bankDetails.setUser(currentuser);

        bankRepository.save(bankDetails);

        return "success";
    }

     public String saveDesc(DescriptionDto descriptionDto, Principal principal) {
        Description description= new Description();
        description.setDescription(descriptionDto.getDescription());
        description.setTitle(descriptionDto.getTitle());

        User currentuser=userRepository.findByEmail(principal.getName());
        description.setUser(currentuser);

        descriptionRepository.save(description);

        return "success";
    }


    @Override
    public PersonalDto getPersonalDto(User user) {
        PersonalDetails personalDetails=personalRepository.findByUserId(user.getId());
        PersonalDto personalDto=new PersonalDto();
        if(personalDetails!=null){
            personalDto.setAll(personalDetails);
        }
        return personalDto;
    }

    @Override
    public EducationDto getEducationDto(User user) {
        EducationDetails educationDetails = educationRepository.findByUserId(user.getId());
        EducationDto educationDto = new EducationDto();
        if (educationDetails != null) educationDto.setAll(educationDetails);
       return educationDto;
    }

    @Override
    public BankDto getBankDto(User user) {
        BankDetails bankDetails=bankRepository.findByUserId(user.getId());
        BankDto bankDto=new BankDto();
        if(bankDetails!=null) bankDto.setAll(bankDetails);
       return bankDto;
    }

    /*@Override
    public DescriptionDto getDescription(User user) {
        Description description=descriptionRepository.findByUserId(user.getId());
        DescriptionDto descriptionDto=new DescriptionDto();
        if(description!=null) descriptionDto.setAll(description);
        return descriptionDto;
        
    }*/

 
    @Override
    public List<DescriptionDto> findAllUsers() {
        // Optional<Description> descriptionOptional = descriptionRepository.findAllById(user.getId());
        // Description users = descriptionOptional.orElseGet(Description::new);
        // return descriptionOptional.stream()
        //         .map((description) -> mapToUserDto(description))
        //         .collect(Collectors.toList());
        List<Description> descriptionss=descriptionRepository.findAll();
        return descriptionss.stream()
                            .map((descriptions) ->mapToUserDto(descriptions))
                            .collect(Collectors.toList());
    }

    private DescriptionDto mapToUserDto(Description description){
        DescriptionDto descriptionDto = new DescriptionDto();
        descriptionDto.setDescription(description.getDescription());
        descriptionDto.setTitle(description.getTitle());
        descriptionDto.setId(description.getId());
        return descriptionDto;
    }

    public Description getTaskById(Long taskId) {
        return descriptionRepository.findById(taskId).orElse(null);
    }
}
