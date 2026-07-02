package com.retailflow.retailflow.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InventoryUpdateResp {
    private Long productId;
    private String productName;
    private Long stock;
}
