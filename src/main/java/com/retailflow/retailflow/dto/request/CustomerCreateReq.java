package com.retailflow.retailflow.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerCreateReq {
    
    @NotBlank(message = "Name cannot be blank")
    @Pattern(regexp = "^[A-Za-z\\s]+$", message = "Name cannot contain numbers or special characters")
    private String name;
    
    @NotBlank(message = "Contact number cannot be blank")
    @Pattern(regexp = "^\\d{11}$", message = "Phone number must be exactly 11 digits")
    private String  contactNumber;
    
    @NotBlank(message = "Address cannot be blank")
    private String address;
}
