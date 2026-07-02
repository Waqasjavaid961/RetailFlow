package com.retailflow.retailflow.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SaleCreateResp {
    private String paymentType;
    private CustomerCreateResp customerCreateResp;
    private List<SaleItemResp> saleItems;
    private BigDecimal total;
    private String saleStatus;

}
