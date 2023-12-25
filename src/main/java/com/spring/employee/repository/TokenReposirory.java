package com.spring.employee.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spring.employee.model.PasswordResetToken;

public interface TokenReposirory extends JpaRepository<PasswordResetToken,Integer>{
    PasswordResetToken findByToken(String token);
}
