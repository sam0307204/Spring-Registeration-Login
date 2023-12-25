package com.spring.employee.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spring.employee.model.Role;

public interface RoleRepository extends JpaRepository<Role,Long> {
    Role findByName(String name);
}
