package com.retailflow.retailflow.dto.request;

import jdk.jfr.Name;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserLoginReq {
    
    @NotBlank(message = "Email cannot be blank")
    @Email(message = "Must be a valid email format")
    @Pattern(regexp = "^[\\w-\\.]+@gmail\\.com$", message = "Email must be a @gmail.com address")
    private String email;
    
    @NotBlank(message = "Password cannot be blank")
    private String password;
}
