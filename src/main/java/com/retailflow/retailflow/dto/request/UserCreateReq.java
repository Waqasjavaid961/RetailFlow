package com.retailflow.retailflow.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserCreateReq {
    
    @NotBlank(message = "Name cannot be blank")
    @Pattern(regexp = "^[A-Za-z\\s]+$", message = "Name cannot contain numbers or special characters")
    private String name;
    
    @NotBlank(message = "Email cannot be blank")
    @Email(message = "Must be a valid email format")
    @Pattern(regexp = "^[\\w-\\.]+@gmail\\.com$", message = "Email must be a @gmail.com address")
    private String email;
    
    @NotBlank(message = "Password cannot be blank")
    @Size(min = 8, message = "Password must be at least 8 characters long")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d@$!%*#?&]+$", message = "Password must contain at least one letter and one number")
    private String password;
}
