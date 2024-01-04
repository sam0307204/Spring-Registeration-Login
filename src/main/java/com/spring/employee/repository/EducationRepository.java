package com.spring.employee.repository;

import org.springframework.data.repository.CrudRepository;

import com.spring.employee.model.EducationDetails;

public interface EducationRepository extends CrudRepository<EducationDetails, Long> {
    EducationDetails findByUserId(Long userId);
}
