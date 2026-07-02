package com.retailflow.retailflow.mapper;

import com.retailflow.retailflow.dto.request.StoreCreateReq;
import com.retailflow.retailflow.dto.response.StoreCreateResp;
import com.retailflow.retailflow.model.Store;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;


@Mapper(componentModel = "spring")
public interface StoreMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "product", ignore = true)
    @Mapping(target = "sales", ignore = true)
    @Mapping(target = "installmentPlans", ignore = true)
    Store toEntity(StoreCreateReq req);

    StoreCreateResp fromEntity(Store store);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "product", ignore = true)
    @Mapping(target = "sales", ignore = true)
    @Mapping(target = "installmentPlans", ignore = true)
    void updateEntityFromReq(StoreCreateReq req, @MappingTarget Store existing);

    List<StoreCreateResp> toListEntity(List<Store> stores);
}
