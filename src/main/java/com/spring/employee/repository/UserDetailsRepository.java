package com.spring.employee.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.spring.employee.model.UserDetails;

@Repository
public interface UserDetailsRepository extends CrudRepository<UserDetails, Long>{
    UserDetails findByUserId(Long userId);
}
