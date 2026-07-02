package com.retailflow.retailflow.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class StoreCreateResp {
    private Long id;
    private String storeName;
    private String contactNumber;
    private String address;
    private String email;
    private boolean isActive;
    private String token;
}
