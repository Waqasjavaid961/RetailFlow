package com.retailflow.retailflow.service.implementation;

import com.retailflow.retailflow.dto.request.*;
import com.retailflow.retailflow.dto.response.InstallmentPlanDtoResp;
import com.retailflow.retailflow.dto.response.SaleCreateResp;
import com.retailflow.retailflow.enums.InstallmentStatus;
import com.retailflow.retailflow.enums.PaymentType;
import com.retailflow.retailflow.enums.SaleStatus;
import com.retailflow.retailflow.exceptions.*;
import com.retailflow.retailflow.helperMethods.InventoryHelperMethod;
import com.retailflow.retailflow.helperMethods.ProductHelperMethod;
import com.retailflow.retailflow.helperMethods.SaleHelperMethod;
import com.retailflow.retailflow.helperMethods.StoreHelperMethod;
import com.retailflow.retailflow.mapper.SaleMapper;
import com.retailflow.retailflow.model.*;
import com.retailflow.retailflow.repositery.*;
import com.retailflow.retailflow.service.interfaces.InstallmentPlanService;
import com.retailflow.retailflow.service.interfaces.SaleService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class SaleServiceImpl implements SaleService {
    private final CustomerRepositery customerRepositery;
    private final InventoryRepositery inventoryRepositery;
    private final SaleRespositery saleRespositery;
    private final SaleMapper saleMapper;
    private final InstallmentPlanService installmentPlanService;
    private final StoreHelperMethod storeHelperMethod;
    private  final SaleHelperMethod saleHelperMethod;
    private final ProductHelperMethod productHelperMethod;
    private final InventoryHelperMethod inventoryHelperMethod;
    private final InstallmentPlanRepositery installmentPlanRepositery;

    public SaleServiceImpl(CustomerRepositery customerRepositery, InventoryRepositery inventoryRepositery, SaleRespositery saleRespositery, SaleMapper saleMapper, InstallmentPlanService installmentPlanService, StoreHelperMethod storeHelperMethod, SaleHelperMethod saleHelperMethod, ProductHelperMethod productHelperMethod, InventoryHelperMethod inventoryHelperMethod, InstallmentPlanRepositery installmentPlanRepositery) {
        this.customerRepositery = customerRepositery;
        this.inventoryRepositery = inventoryRepositery;
        this.saleRespositery = saleRespositery;
        this.saleMapper = saleMapper;
        this.installmentPlanService = installmentPlanService;
        this.storeHelperMethod = storeHelperMethod;
        this.saleHelperMethod = saleHelperMethod;
        this.productHelperMethod = productHelperMethod;
        this.inventoryHelperMethod = inventoryHelperMethod;
        this.installmentPlanRepositery = installmentPlanRepositery;
    }

    /**
     * take sale items from request and check each product exists in this store or not
     * then check inventory if stock is available or not
     * if stock available then deduct it and add item to saleItem list
     * at the end save all updated inventories and return the list
     */
    private List<SaleItem> saleItems(List<SaleItemReq>req,Store store){
        List<SaleItem> saleItemList=new ArrayList<>();
        List<Inventory>inventoryToUpdate=new ArrayList<>();
        for(SaleItemReq req1:req){
            Product product=productHelperMethod.findProductByStoreAndProductId(store,req1.getProductId());
            Inventory inventory=inventoryHelperMethod.findByProduct(product);
            if(inventory.getStock()<req1.getQuantity()){
                throw new OutOfStockException("this product is out of stock");
            }
            BigDecimal lineTotal=product.getPrice().multiply(BigDecimal.valueOf(req1.getQuantity()));
            SaleItem saleItem=new SaleItem();
            saleItem.setQuantity(req1.getQuantity());
            saleItem.setProduct(product);
            saleItem.setLineTotal(lineTotal);
            saleItem.setUnitPrice(product.getPrice());
            saleItem.setProductName(product.getProductName());
            saleItem.setProductId(product.getProductId());
            saleItemList.add(saleItem);
            Long inventoryUpdate=inventory.getStock()-req1.getQuantity();
            inventory.setStock(inventoryUpdate);
            inventoryToUpdate.add(inventory);
        }
        inventoryRepositery.saveAll(inventoryToUpdate);
        return saleItemList;
    }

    /**
     * check the customer by contact number if customer already exists then return it
     * if customer not found then create a new customer object
     * this works for both registered customer and walk-in customer
     */
    private Customer getOrCreateCustomer(CustomerCreateReq req){
        return customerRepositery.findByContactNumber(req.getContactNumber()).
                orElseGet(()->{Customer newcustomer=new Customer();
                    newcustomer.setContactNumber(req.getContactNumber());
                    newcustomer.setCustomerName(req.getName());
                    newcustomer.setAddress(req.getAddress());
                    return newcustomer;});


    }

    /**
     * check the payment type and save the customer accordingly
     * if payment is cash then customer is optional we save if provided
     * if payment is installment then customer is required otherwise throw exception
     */
    private PaymentType handlePayment(PaymentType paymentType,Customer customer){
        if(paymentType==PaymentType.cash){
            if(customer!=null){

                customerRepositery.save(customer);
            }
        }
        if(paymentType==PaymentType.installment){
            if(customer==null){
                throw new CustomerNotFoundException("customer data must required for installment options");
            }
            customerRepositery.save(customer);

        }
        return paymentType;
    }


    /**
     * create a new sale for the logged in store
     * first validate payment type then get sale items and deduct inventory
     * calculate total amount from all sale items
     * if payment type is cash then save sale directly
     * if payment type is installment then save sale and create installment plan
     */
    @Transactional
    @Override
   public SaleCreateResp createSale(SaleCreateReq req) {
        PaymentType paymentType=PaymentType.toString(req.getPaymentType());
        if(paymentType==null){
            throw new PayementTypeException("wrong payment type");
        }
        Store store=storeHelperMethod.getLoginStore();
        List<SaleItem>saleItemList=saleItems(req.getItems(),store);
        Sale sale =new Sale();
        sale.setSaleDate(LocalDateTime.now());
        sale.setSaleItems(saleItemList);
        sale.setStore(store);
        sale.setStatus(SaleStatus.completed);
        BigDecimal totalAmount=BigDecimal.ZERO;
        for(SaleItem item:saleItemList){
            totalAmount=totalAmount.add(item.getLineTotal());
            item.setSale(sale);
        }
        sale.setTotalAmount(totalAmount);
        Customer customer=null;
        if(req.getCustomerCreateReq()!=null) {
            customer = getOrCreateCustomer(req.getCustomerCreateReq());
        }
        PaymentType paymentType1=handlePayment(paymentType,customer);
        sale.setPaymentType(paymentType1);
        if(paymentType1==PaymentType.cash){
            if(req.getCustomerCreateReq()!=null) {
                sale.setCustomer(customer);
            }
        }
       else if(paymentType1==PaymentType.installment){
            if(req.getCustomerCreateReq()==null){
                throw new CustomerNotFoundException("customer not found");
            }
            if(req.getPlanReq()==null){
                throw new InstallmentDataNotFoundException("installment data not found");
            }
            sale.setCustomer(customer);
            Sale savedSale=saleRespositery.save(sale);

            InstallmentPlanDtoResp resp=installmentPlanService.createInstallmentPlan(store,savedSale,customer,req.getPlanReq());
            return saleMapper.fromEntity(savedSale);
        }
            Sale savedSale = saleRespositery.save(sale);
            return saleMapper.fromEntity(savedSale);

    }

    /**
     * process full refund for a sale
     * first check the sale is not already refunded
     * then check this sale belongs to the logged in store
     * add back all quantities to inventory stock
     * change sale status to refund
     * if sale was on installment then cancel the installment plan too
     */
    @Transactional
    @Override
    public SaleCreateResp refundSale(RefundSaleReq req) {
        Sale sale=saleHelperMethod.findSaleById(req.getSaleId());
        if(sale.getStatus()== SaleStatus.refund){
            throw new AlreadyRefundException("this product is already refund");
        }
        Store store=storeHelperMethod.getLoginStore();
        if(!sale.getStore().getId().equals(store.getId())){
            throw new StoreNotFoundException("the product is not belong to this  store");
        }
        for(SaleItem saleItem:sale.getSaleItems()){
            Product product=saleItem.getProduct();
            Inventory inventory=inventoryHelperMethod.findByProduct(product);
            Long refundedQuantity=inventory.getStock()+saleItem.getQuantity();
            inventory.setStock(refundedQuantity);
            inventory.setProduct(product);
            inventoryRepositery.save(inventory);
        }
        sale.setStatus(SaleStatus.refund);
        Sale savedSale=saleRespositery.save(sale);
        InstallmentPlan installmentPlan=null;
        if(savedSale.getPaymentType()==PaymentType.installment){
            installmentPlan=installmentPlanRepositery.findBySale(savedSale);
        }
        if(installmentPlan!=null) {
            installmentPlan.setStatus(InstallmentStatus.cancelled);
            installmentPlan.setRemainingAmount(BigDecimal.ZERO);
            installmentPlanRepositery.save(installmentPlan);
        }

        return saleMapper.fromEntity(savedSale);
    }

    /** find sale by its id and return the details */

    @Override
    public SaleCreateResp showSaleById(Long saleId) {
        Sale sale=saleHelperMethod.findSaleById(saleId);
        return saleMapper.fromEntity(sale);
    }
    /** get all sales of the logged in store with pagination */
    @Override
    public Page<SaleCreateResp> showAllSales(int page, int size) {
        Pageable pageable= PageRequest.of(page,size);
        Store store=storeHelperMethod.getLoginStore();
        Page<Sale>sales=saleRespositery.findByStoreId(store.getId(),pageable);
        if(sales.isEmpty()){
            throw new SaleNotFoundException("sales not found");
        }
        return sales.map(saleMapper::fromEntity);

    }


}
