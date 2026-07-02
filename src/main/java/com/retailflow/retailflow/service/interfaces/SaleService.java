package com.retailflow.retailflow.service.interfaces;

import com.retailflow.retailflow.dto.request.CustomerCreateReq;
import com.retailflow.retailflow.dto.request.RefundSaleReq;
import com.retailflow.retailflow.dto.request.SaleCreateReq;
import com.retailflow.retailflow.dto.response.SaleCreateResp;
import com.retailflow.retailflow.model.Customer;
import org.springframework.data.domain.Page;

import java.util.List;

public interface SaleService {
    SaleCreateResp createSale(SaleCreateReq req);
    SaleCreateResp refundSale(RefundSaleReq req);
    SaleCreateResp showSaleById(Long saleId);
    Page<SaleCreateResp> showAllSales(int page,int size);


}
