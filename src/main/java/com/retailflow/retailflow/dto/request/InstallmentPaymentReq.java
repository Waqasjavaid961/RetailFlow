package com.retailflow.retailflow.dto.request;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class InstallmentPaymentReq {
    private String contactNumber;
    private Long saleId;
    private BigDecimal amountPaid;

}
