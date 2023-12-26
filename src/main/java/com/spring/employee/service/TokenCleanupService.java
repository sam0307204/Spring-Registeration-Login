package com.spring.employee.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.spring.employee.repository.TokenReposirory;

@Service
public class TokenCleanupService {
    
    @Autowired
    private TokenReposirory tokenReposirory;

    @Scheduled(fixedRate = 5 *60*1000)
    public void cleanupExpiredToken(){
        LocalDateTime now=LocalDateTime.now();
        tokenReposirory.deleteByExpiryDateBefore(now);
    }
}
