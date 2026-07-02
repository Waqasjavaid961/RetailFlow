package com.retailflow.retailflow.mapper;

import com.retailflow.retailflow.dto.response.InstallmentPaymentResp;
import com.retailflow.retailflow.dto.response.InstallmentPlanDtoResp;
import com.retailflow.retailflow.model.InstallmentPayment;
import com.retailflow.retailflow.model.InstallmentPlan;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface InstallmentMapper {

    InstallmentPlanDtoResp fromPlanEntity(InstallmentPlan installmentPlan);

    @Mapping(source = "installmentPlan.sale.saleId", target = "saleId")
    @Mapping(source = "installmentPlan.customer.customerName", target = "name")
    @Mapping(source = "installmentPlan", target = "installmentDetails")
    InstallmentPaymentResp fromPaymentEntity(InstallmentPayment installmentPayment);
    List<InstallmentPlanDtoResp>  fromPlanListEntity(List<InstallmentPlan>planList);
    List<InstallmentPaymentResp> fromPaymentListEntity(List<InstallmentPayment>payments);
}
