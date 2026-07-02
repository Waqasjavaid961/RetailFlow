package com.retailflow.retailflow.dto.response;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerCreateResp {
    private Long customerId;
    private String customerName;
    private String contactNumber;
    private String address;
}
