package com.spring.employee.dto;

import com.spring.employee.model.BankDetails;

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

    public void setAll(BankDetails bankDetails){
        this.accountNumber = bankDetails.getAccountNumber();
        this.ifscCode =bankDetails.getIfscCode();
        this.bankName =bankDetails.getBankName();
        this.branchName =bankDetails.getBranchName();
        this.phoneNumber =bankDetails.getPhoneNumber();
        this.email=bankDetails.getEmail();

    }
}
