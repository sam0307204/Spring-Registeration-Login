package com.spring.employee.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DescriptionDto {
    private Long id;
    private String title;
    private String description;


    /*public void setAll(Description description){
        this.setDescription(description.getDescription());
        this.setTitle(description.getTitle());
    }*/
}
