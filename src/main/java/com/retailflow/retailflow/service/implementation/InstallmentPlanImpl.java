package com.retailflow.retailflow.service.implementation;
import com.retailflow.retailflow.common.ContextHolder;
import com.retailflow.retailflow.dto.request.InstallmentPaymentReq;
import com.retailflow.retailflow.dto.request.InstallmentPlanReq;
import com.retailflow.retailflow.dto.response.InstallmentPaymentResp;
import com.retailflow.retailflow.dto.response.InstallmentPlanDtoResp;
import com.retailflow.retailflow.enums.InstallmentStatus;
import com.retailflow.retailflow.enums.PaymentType;
import com.retailflow.retailflow.exceptions.*;
import com.retailflow.retailflow.helperMethods.CustomerHelperMethods;
import com.retailflow.retailflow.helperMethods.SaleHelperMethod;
import com.retailflow.retailflow.helperMethods.StoreHelperMethod;
import com.retailflow.retailflow.mapper.InstallmentMapper;
import com.retailflow.retailflow.model.*;
import com.retailflow.retailflow.repositery.*;
import com.retailflow.retailflow.service.interfaces.InstallmentPlanService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.net.ContentHandler;
import java.time.LocalDate;
import java.util.List;

@Service
public class InstallmentPlanImpl implements InstallmentPlanService {
    private final SaleRespositery saleRespositery;
    private final CustomerRepositery customerRepositery;
    private final InstallmentPlanRepositery planRepositery;
    private final InstallmentPaymentRepositery installmentPaymentRepositery;
    private final InstallmentMapper installmentMapper;
    private final CustomerHelperMethods customerHelperMethods;
    private final SaleHelperMethod saleHelperMethod;
    private final StoreHelperMethod storeHelperMethod;
    private final StoreRepositery storeRepositery;

    public InstallmentPlanImpl(SaleRespositery saleRespositery, CustomerRepositery customerRepositery, InstallmentPaymentRepositery paymentRepositery, InstallmentPlanRepositery planRepositery, InstallmentPaymentRepositery installmentPaymentRepositery, InstallmentMapper installmentMapper, CustomerHelperMethods customerHelperMethods, SaleHelperMethod saleHelperMethod, StoreHelperMethod storeHelperMethod, StoreRepositery storeRepositery) {
        this.saleRespositery = saleRespositery;
        this.customerRepositery = customerRepositery;
        this.planRepositery = planRepositery;
        this.installmentPaymentRepositery = installmentPaymentRepositery;
        this.installmentMapper = installmentMapper;
        this.customerHelperMethods = customerHelperMethods;
        this.saleHelperMethod = saleHelperMethod;
        this.storeHelperMethod = storeHelperMethod;
        this.storeRepositery = storeRepositery;
    }

    /**
     * create installment plan for a sale
     * first find the sale and customer from database
     * then set down payment and calculate remaining amount
     * divide remaining amount by total months to get monthly installment
     * set start date as today and end date after total months
     */

    @Override
    public InstallmentPlanDtoResp createInstallmentPlan(Store store,Sale sale, Customer customer, InstallmentPlanReq req) {
        sale=saleHelperMethod.findSaleById(sale.getSaleId());
        customer=customerHelperMethods.findByContactNumber(customer.getContactNumber());

        com.retailflow.retailflow.model.InstallmentPlan installmentPlan=new com.retailflow.retailflow.model.InstallmentPlan();
        installmentPlan.setSale(sale);
        installmentPlan.setStore(store);
        installmentPlan.getSale().setPaymentType(PaymentType.installment);
        installmentPlan.setCustomer(customer);
        installmentPlan.setTotalAmount(sale.getTotalAmount());
        if(req.getDownPayment() == null) {
            throw new DownPaymentValidationException("Down payment cannot be null. Please check your request JSON keys.");
        }
        if(installmentPlan.getTotalAmount().compareTo(req.getDownPayment())<0){
            throw new DownPaymentValidationException("down payment is higer to actual payment");
        }
        installmentPlan.setDownPayment(req.getDownPayment());
        BigDecimal remainingAmount=installmentPlan.getTotalAmount().subtract(installmentPlan.getDownPayment());
        installmentPlan.setRemainingAmount(remainingAmount);
        if(req.getTotalMonths()==null){
            req.setTotalMonths(10L);// set default
        }

        BigDecimal monthlyInstallment = installmentPlan.getRemainingAmount()
                .divide(BigDecimal.valueOf(req.getTotalMonths()), 2, java.math.RoundingMode.HALF_UP);
        installmentPlan.setMonthlyInstallment(monthlyInstallment);
        installmentPlan.setStartDate(LocalDate.now());
        if(installmentPlan.getRemainingAmount().compareTo(BigDecimal.ZERO)==0){
            installmentPlan.setStatus(InstallmentStatus.completed);
        }else {
            installmentPlan.setStatus(InstallmentStatus.pending);
        }
        LocalDate endDate=LocalDate.now().plusMonths(req.getTotalMonths());
        installmentPlan.setEndDate(endDate);
        com.retailflow.retailflow.model.InstallmentPlan savedPlan=planRepositery.save(installmentPlan);
        return installmentMapper.fromPlanEntity(savedPlan);
    }

    /**
     * pay an installment for existing plan
     * first check the customer belongs to this sale
     * check the sale payment type is installment
     * check the plan is not already completed or cancelled
     * check paid amount is not less than monthly installment
     * check paid amount is not more than remaining amount
     * then deduct paid amount from remaining and save payment record
     */
    @Transactional
    @Override
    public InstallmentPaymentResp payInstallment(InstallmentPaymentReq req) {
        Customer customer=customerHelperMethods.findByContactNumber(req.getContactNumber());
        Sale sale=saleHelperMethod.findSaleById(req.getSaleId());
        if(!sale.getCustomer().equals(customer)){
            throw new CustomerNotFoundException("this customer is not belong to this sales");
        }

        if(sale.getPaymentType()!=PaymentType.installment){
            throw new InstallmentDataNotFoundException("this Sale id is not in installment");
        }
        InstallmentPlan installmentPlan=planRepositery.findBySale(sale);
        if(installmentPlan==null){
            throw new InstallmentDataNotFoundException("Installment plan not found");
        }
        if(installmentPlan.getStatus()==InstallmentStatus.completed){
            throw new InstallmentDataNotFoundException("your installment already cleared");
        }
        if(installmentPlan.getStatus()==InstallmentStatus.cancelled){
            throw new InstallmentDataNotFoundException("your installment already cancelled");
        }
        if(installmentPlan.getRemainingAmount().compareTo(BigDecimal.ZERO)==0){
            throw new InvalidAmountException("you have not remaining installment amount");
        }
        if(req.getAmountPaid().compareTo(BigDecimal.ZERO)<=0){
            throw new InvalidAmountException(" amount must be in postive digits");
        }
        if (req.getAmountPaid().compareTo(installmentPlan.getRemainingAmount()) > 0) {
            throw new InvalidAmountException("Paid amount cannot be greater than the total remaining amount (" + installmentPlan.getRemainingAmount() + ")");
        }

        BigDecimal remainingInstallment=installmentPlan.getRemainingAmount().compareTo(installmentPlan.getMonthlyInstallment())<0 ?
                installmentPlan.getRemainingAmount() :
                installmentPlan.getMonthlyInstallment();
        if(req.getAmountPaid().compareTo(remainingInstallment)<0){
            throw new InvalidAmountException("paid amount must be equal to  installment");
        }
        BigDecimal newRemainingAmount=installmentPlan.getRemainingAmount().subtract(req.getAmountPaid());
        installmentPlan.setRemainingAmount(newRemainingAmount);
        InstallmentPayment installmentPayment=new InstallmentPayment();
        installmentPayment.setPaymentDate(LocalDate.now());
        installmentPayment.setAmountPaid(req.getAmountPaid());
        installmentPayment.setInstallmentPlan(installmentPlan);
        if(installmentPlan.getRemainingAmount().compareTo(BigDecimal.ZERO)==0){
            installmentPlan.setStatus(InstallmentStatus.completed);
        }
        planRepositery.save(installmentPlan);
        InstallmentPayment savedPayment=installmentPaymentRepositery.save(installmentPayment);
        installmentPlan.setCustomer(customer);
        installmentPlan.setSale(sale);
        savedPayment.setInstallmentPlan(installmentPlan);

        return installmentMapper.fromPaymentEntity(savedPayment);

    }

    /** get installment plan by sale id (used internally) */
    @Override
    public InstallmentPlan getInstallmentPlan(Long saleId) {
        Sale sale=saleHelperMethod.findSaleById(saleId);
        if(sale.getPaymentType()!=PaymentType.installment){
            throw new InstallmentDataNotFoundException("this sale is not in installment");
        }
        InstallmentPlan installmentPlan=planRepositery.findBySale(sale);
        if(installmentPlan==null){
            throw new InstallmentDataNotFoundException("installment not found");
        }
        return installmentPlan;

    }

    /** show installment plan details for a customer and sale */
    @Override
    public InstallmentPlanDtoResp showInstallmentPlan(String contactNumber,Long saleId) {
        Customer customer=customerHelperMethods.findByContactNumber(contactNumber);
        if(customer==null){
            throw new CustomerNotFoundException("customer not found ye yaha error hei ");
        }
        Sale sale=saleHelperMethod.findSaleById(saleId);
        if(sale==null || !sale.getCustomer().equals(customer)){
            throw new SaleNotFoundException("sales not found of this customer");
        }
        InstallmentPlan installmentPlan=planRepositery.findBySale(sale);
        return installmentMapper.fromPlanEntity(installmentPlan);

        
    }

    /** show all installment plans of a customer with pagination */
    @Override
    public Page<InstallmentPlanDtoResp> showAllInstallments(String contactNumber,int page,int size) {
        Customer customer=customerHelperMethods.findByContactNumber(contactNumber);
        if(customer==null){
            throw new CustomerNotFoundException("customer not found");
        }
        Pageable pageable=PageRequest.of(page,size);
        Page<InstallmentPlan>planList =planRepositery.findByCustomer(customer,pageable);
        if(planList.isEmpty()){
            throw new InstallmentDataNotFoundException("installments not found of this customer");
        }
        return planList.map(installmentMapper::fromPlanEntity);
    }

    /** show all payment history of a specific installment plan with pagination */
    @Override
    public Page<InstallmentPaymentResp> showALlPaymentsBySaleId(String contactNumber, Long saleId,int page,int size) {
        Customer customer=customerHelperMethods.findByContactNumber(contactNumber);
        if(customer==null){
            throw new CustomerNotFoundException("customer not found");
        }
        Sale sale=saleHelperMethod.findSaleById(saleId);
        if(sale==null || !sale.getCustomer().equals(customer)){
            throw new SaleNotFoundException("history not found of this customer");
        }
        InstallmentPlan installmentPlan=planRepositery.findBySale(sale);
        if(installmentPlan==null){
            throw new InstallmentDataNotFoundException("installment plan not found");
        }
        Pageable pageable= PageRequest.of(page,size);
        Page<InstallmentPayment>paymentList=installmentPaymentRepositery.findByInstallmentPlan(installmentPlan,pageable);
        if(paymentList.isEmpty()){
            throw new PayementTypeException("payments not found");
        }
        return paymentList.map(installmentMapper::fromPaymentEntity);
    }
    /** show all installment plans of the current logged in store with pagination */
    public Page<InstallmentPlanDtoResp> showAllStoreInstallments(int page,int size){
        Long storeId= ContextHolder.getStoreId();
        Store store=storeRepositery.findById(storeId).orElseThrow(()->new StoreNotFoundException("store not found"));
        Pageable pageable=PageRequest.of(page,size);
        Page<InstallmentPlan>plans=planRepositery.findByStore(store,pageable);
        return plans.map(installmentMapper::fromPlanEntity);
    }

}
