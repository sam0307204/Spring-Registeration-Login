package com.spring.employee.service;

import java.util.List;
import com.spring.employee.dto.UserDto;
import com.spring.employee.model.User;

public interface UserService {
    void saveUser(UserDto userDto);

    User findByEmail(String email);

    List<UserDto> findAllUsers();
}
