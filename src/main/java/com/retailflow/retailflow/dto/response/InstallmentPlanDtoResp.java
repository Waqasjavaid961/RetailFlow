package com.retailflow.retailflow.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class InstallmentPlanDtoResp {
    private Long planId;
    private BigDecimal totalAmount;
    private BigDecimal downPayment;
    private BigDecimal remainingAmount;
    private BigDecimal monthlyInstallment;
    private LocalDate startDate;
    private LocalDate endDate;
    private String  status;

}
