package com.retailflow.retailflow.mapper;

import com.retailflow.retailflow.dto.request.ProductCreateReq;
import com.retailflow.retailflow.dto.response.ProductCreateResp;
import com.retailflow.retailflow.model.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;


@Mapper(componentModel = "spring")
public interface ProductMapper {

    @Mapping(target = "category", ignore = true)
    @Mapping(target = "inventory", ignore = true)
    @Mapping(target = "store", ignore = true)
    @Mapping(target = "productId", ignore = true)
    Product toEntity(ProductCreateReq req);

    ProductCreateResp fromEntity(Product product);

    @Mapping(target = "category", ignore = true)
    @Mapping(target = "inventory", ignore = true)
    @Mapping(target = "store", ignore = true)
    @Mapping(target = "productId", ignore = true)
    void updateEntityFromReq(ProductCreateReq req, @MappingTarget Product product);

    // Converts a list of Product entities to a list of Product response DTOs
    List<ProductCreateResp> toListEntity(List<Product> product);
}
