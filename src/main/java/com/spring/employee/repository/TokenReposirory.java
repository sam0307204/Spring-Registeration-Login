package com.spring.employee.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.spring.employee.model.PasswordResetToken;

public interface TokenReposirory extends JpaRepository<PasswordResetToken,Integer>{
    PasswordResetToken findByToken(String token);

    //void deleteByExpiryDateBefore(LocalDateTime expiryDateTime);
    // @Modifying
    // @Transactional
    // @Query(value="DELETE FROM passwordresettoken  WHERE expiry_date_time<= CURRENT_TIMESTAMP - INTERVAL :expirationMinutes MINUTE",nativeQuery=true)
    // void deleteExpiredRows(@Param("expirationMinutes") int expirationMinutes);
    //void deleteExpiredRows();
    // void deleteExpiredRows();
    //void deleteByExpiryTimeLessThen(LocalDateTime now);
    //DELETE FROM your_table_name
    //WHERE expiry_date <= NOW();
}
