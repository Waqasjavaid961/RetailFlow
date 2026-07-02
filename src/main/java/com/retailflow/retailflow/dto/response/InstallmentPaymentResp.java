package com.retailflow.retailflow.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class InstallmentPaymentResp {
    private Long saleId;
    private String name;
    private InstallmentPlanDtoResp installmentDetails;
    private BigDecimal amountPaid;

}
