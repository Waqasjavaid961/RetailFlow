package com.retailflow.retailflow.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;


@NoArgsConstructor
@AllArgsConstructor
@Data
public class ProductCreateReq {
    private Long id;
    private String category;
    private String productName;
    private String modelNumber;
    private Long productQuantity;
    private BigDecimal price;
    private String description;
}
