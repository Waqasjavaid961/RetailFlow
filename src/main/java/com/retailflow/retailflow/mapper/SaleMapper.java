package com.retailflow.retailflow.mapper;

import com.retailflow.retailflow.dto.response.CustomerCreateResp;
import com.retailflow.retailflow.dto.response.SaleCreateResp;
import com.retailflow.retailflow.dto.response.SaleItemResp;
import com.retailflow.retailflow.model.Customer;
import com.retailflow.retailflow.model.Sale;
import com.retailflow.retailflow.model.SaleItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SaleMapper {

    @Mapping(source = "totalAmount", target = "total")
    @Mapping(source = "customer", target = "customerCreateResp")
    @Mapping(source = "status", target = "saleStatus")
    SaleCreateResp fromEntity(Sale sale);

    @Mapping(source = "product.productId", target = "productId")
    @Mapping(source = "product.productName", target = "productName")
    @Mapping(source = "unitPrice", target = "unitPrice")
    SaleItemResp toSaleItemResp(SaleItem saleItem);

    CustomerCreateResp toCustomer(Customer customer);

    List<SaleCreateResp> fromEntityList(List<Sale> sales);
}