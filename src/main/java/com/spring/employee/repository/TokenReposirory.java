package com.spring.employee.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spring.employee.model.PasswordResetToken;

import java.time.LocalDateTime;

public interface TokenReposirory extends JpaRepository<PasswordResetToken,Integer>{
    PasswordResetToken findByToken(String token);

    void deleteByExpiryDateBefore(LocalDateTime expiryDate);
}
