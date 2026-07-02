package com.retailflow.retailflow.helperMethods;

import com.retailflow.retailflow.exceptions.SaleNotFoundException;
import com.retailflow.retailflow.model.Sale;
import com.retailflow.retailflow.model.Store;
import com.retailflow.retailflow.repositery.SaleRespositery;
import org.springframework.stereotype.Component;

import java.awt.*;

@Component
public class SaleHelperMethod {
    private final SaleRespositery saleRespositery;
    private final StoreHelperMethod storeHelperMethod;

    public SaleHelperMethod(SaleRespositery saleRespositery, StoreHelperMethod storeHelperMethod) {
        this.saleRespositery = saleRespositery;
        this.storeHelperMethod = storeHelperMethod;
    }
    public Sale findSaleById(Long saleId){
        Store store=storeHelperMethod.getLoginStore();

        return saleRespositery.findBySaleIdAndStore(saleId,store).orElseThrow(()->new SaleNotFoundException("sales not found"));
    }

}
