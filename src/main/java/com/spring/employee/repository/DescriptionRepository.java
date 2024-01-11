package com.spring.employee.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.spring.employee.model.Description;

public interface DescriptionRepository extends JpaRepository<Description,Long>{
    
    List<Description> findByUserId(Long userId);

    //Optional<Description> findById(Long id);
    @Query("SELECT d FROM Description d WHERE d.id= :id")
    List<Description> findDublicateById(Long id);
}
