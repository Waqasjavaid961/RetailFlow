package com.retailflow.retailflow.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StoreCreateReq {
    
    @NotBlank(message = "Store name cannot be blank")
    private String storeName;
    
    @NotBlank(message = "Category cannot be blank")
    private String category;
    
    @NotBlank(message = "Contact number cannot be blank")
    @Pattern(regexp = "^\\d{11}$", message = "Contact number must be exactly 11 digits")
    private String contactNumber;
    
    @NotBlank(message = "Address cannot be blank")
    private String address;
}
