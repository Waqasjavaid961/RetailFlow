package com.retailflow.retailflow.service.interfaces;

import com.retailflow.retailflow.dto.request.InstallmentPaymentReq;
import com.retailflow.retailflow.dto.request.InstallmentPlanReq;
import com.retailflow.retailflow.dto.response.InstallmentPaymentResp;
import com.retailflow.retailflow.dto.response.InstallmentPlanDtoResp;
import com.retailflow.retailflow.model.Customer;
import com.retailflow.retailflow.model.InstallmentPlan;
import com.retailflow.retailflow.model.Sale;
import com.retailflow.retailflow.model.Store;
import org.springframework.data.domain.Page;

import java.util.List;

public interface InstallmentPlanService {
    InstallmentPlanDtoResp createInstallmentPlan(Store store,Sale sale, Customer customer, InstallmentPlanReq req);
    InstallmentPaymentResp payInstallment(InstallmentPaymentReq req);
    InstallmentPlan getInstallmentPlan(Long id);
    InstallmentPlanDtoResp showInstallmentPlan(String contactNumber,Long id);
    Page<InstallmentPlanDtoResp> showAllInstallments(String contactNumber,int page,int size);
    Page<InstallmentPaymentResp> showALlPaymentsBySaleId(String contactNumber,Long id,int page,int size);

}
