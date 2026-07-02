package com.retailflow.retailflow.controller;

import com.retailflow.retailflow.common.ResponseApi;
import com.retailflow.retailflow.dto.request.InstallmentPaymentReq;
import com.retailflow.retailflow.dto.response.InstallmentPaymentResp;
import com.retailflow.retailflow.dto.response.InstallmentPlanDtoResp;
import com.retailflow.retailflow.service.implementation.InstallmentPlanImpl;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.awt.print.Pageable;
import java.util.List;

@RestController
@RequestMapping("/api/installments")
public class InstallmentController {
    private final InstallmentPlanImpl installmentPlan;

    public InstallmentController(InstallmentPlanImpl installmentPlan) {
        this.installmentPlan = installmentPlan;
    }

    @PreAuthorize("hasAnyRole('owner', 'customer')")
    @PostMapping("pay")
    public ResponseEntity<ResponseApi<InstallmentPaymentResp>> payInstallment(@RequestBody InstallmentPaymentReq req){
        InstallmentPaymentResp resp=installmentPlan.payInstallment(req);
        return ResponseEntity.status(HttpStatus.OK).body(ResponseApi.success("Installment paid successfully", resp));
    }
    
    @PreAuthorize("hasAnyRole('owner', 'customer')")
    @GetMapping("plan")
    public ResponseEntity<ResponseApi<InstallmentPlanDtoResp>> showInstallmentPlanDetails(@RequestParam String contactNumber, @RequestParam Long saleId){
        InstallmentPlanDtoResp resp=installmentPlan.showInstallmentPlan(contactNumber, saleId);
        return ResponseEntity.status(HttpStatus.OK).body(ResponseApi.success("Plan fetched successfully", resp));
    }
    
    @PreAuthorize("hasAnyRole('owner', 'customer')")
    @GetMapping("plans")
    public ResponseEntity<ResponseApi<Page<InstallmentPlanDtoResp>>> showAllInstallmentPlans(@RequestParam String contactNumber, @RequestParam(defaultValue = "0")int page, @RequestParam(defaultValue = "10")int size){
        Page<InstallmentPlanDtoResp> list=installmentPlan.showAllInstallments(contactNumber,page,size);
        return ResponseEntity.status(HttpStatus.OK).
                body(ResponseApi.
                        success("Plans fetched successfully",
                                list));
    }
    
    @PreAuthorize("hasAnyRole('owner', 'customer')")
    @GetMapping("payments")
    public ResponseEntity<ResponseApi<Page<InstallmentPaymentResp>>> showAllInstallmentAmounts(@RequestParam String contactNumber, @RequestParam Long saleId,@RequestParam(defaultValue = "0")int page,@RequestParam(defaultValue = "10")int size){

        Page<InstallmentPaymentResp> resps=installmentPlan.showALlPaymentsBySaleId(contactNumber, saleId,page,size);
        return ResponseEntity.status(HttpStatus.OK).
                body(ResponseApi.
                        success("Payments fetched successfully",
                                resps));
    }

    @PreAuthorize("hasRole('owner')")
    @GetMapping("store")
    public ResponseEntity<ResponseApi<Page<InstallmentPlanDtoResp>>>showAllStoreInstallmentPlans(@RequestParam(defaultValue = "0")int page,@RequestParam(defaultValue = "10")int size){
        Page<InstallmentPlanDtoResp> storeInstallments=installmentPlan.showAllStoreInstallments(page,size);
        return ResponseEntity.status(HttpStatus.OK).
                body(ResponseApi.
                        success("Store installments fetched successfully",
                                storeInstallments));
    }
}
