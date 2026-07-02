package com.retailflow.retailflow.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductCreateResp {
    private String category;
    private String productName;
    private String modelNumber;
    private BigDecimal price;
    private String description;
}
