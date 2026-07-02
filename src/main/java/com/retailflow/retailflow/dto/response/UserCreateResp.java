package com.retailflow.retailflow.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserCreateResp {
    private Long id;
    private String name;
    private String email;
    private String role;
}
