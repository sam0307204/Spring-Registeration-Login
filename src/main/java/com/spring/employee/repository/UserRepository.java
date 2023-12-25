package com.spring.employee.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spring.employee.model.User;

public interface UserRepository extends JpaRepository<User,Long>{
    User findByEmail(String email);
}
