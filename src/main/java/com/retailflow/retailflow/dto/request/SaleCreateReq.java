package com.retailflow.retailflow.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SaleCreateReq {

    private String paymentType;
    private CustomerCreateReq customerCreateReq;
    private InstallmentPlanReq planReq;
    private String saleStatus;
    private List<SaleItemReq> items;
}
