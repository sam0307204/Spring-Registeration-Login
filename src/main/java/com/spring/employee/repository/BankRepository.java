package com.spring.employee.repository;

import org.springframework.data.repository.CrudRepository;

import com.spring.employee.model.BankDetails;

public interface BankRepository extends CrudRepository<BankDetails, Long>{
    BankDetails findByUserId(Long userId);
}
