package com.spring.employee.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.spring.employee.model.PersonalDetails;

@Repository
public interface PersonalRepository extends CrudRepository<PersonalDetails, Long> {
    PersonalDetails findByUserId(Long userId);
}
