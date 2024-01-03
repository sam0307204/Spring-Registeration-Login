package com.spring.employee.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BankDto {
    private String accountNumber;
    private String ifscCode;
    private String bankName;
    private String branchName;
    private String phoneNumber;
    private String email;
}
