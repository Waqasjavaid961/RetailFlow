package com.retailflow.retailflow.mapper;

import com.retailflow.retailflow.dto.response.InventoryUpdateResp;
import com.retailflow.retailflow.model.Inventory;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface InventoryMapper {

    @Mapping(source = "product.productId", target = "productId")
    @Mapping(source = "product.productName", target = "productName")
    InventoryUpdateResp fromEntity(Inventory inventory);
}
