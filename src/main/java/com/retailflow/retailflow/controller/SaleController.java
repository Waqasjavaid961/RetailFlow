package com.retailflow.retailflow.controller;

import com.retailflow.retailflow.common.ResponseApi;
import com.retailflow.retailflow.dto.request.RefundSaleReq;
import com.retailflow.retailflow.dto.request.SaleCreateReq;
import com.retailflow.retailflow.dto.response.SaleCreateResp;
import com.retailflow.retailflow.service.interfaces.SaleService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.awt.print.Pageable;
import java.util.List;

@RestController
@RequestMapping("/api/shared/sales")
public class SaleController {
    private final SaleService saleService;

    public SaleController(SaleService saleService) {
        this.saleService = saleService;
    }
    @PreAuthorize("hasRole('owner')")
    @PostMapping
    public ResponseEntity<ResponseApi<SaleCreateResp>>createSale(@RequestBody SaleCreateReq req){
        SaleCreateResp resp=saleService.createSale(req);
        return ResponseEntity.status(HttpStatus.OK).body(ResponseApi.success("Sale created successfully", resp));
    }
    @PreAuthorize("hasRole('owner')")
    @PostMapping("refund")
    public ResponseEntity<ResponseApi<SaleCreateResp>>refund(@RequestBody RefundSaleReq req){
        SaleCreateResp saleCreateResp=saleService.refundSale(req);
        return ResponseEntity.status(HttpStatus.OK).body(ResponseApi.success("Sale refunded successfully", saleCreateResp));
    }
    @PreAuthorize("hasAnyRole('owner', 'customer')")
    @GetMapping("{id}")
    public ResponseEntity<ResponseApi<SaleCreateResp>>showById(@PathVariable("id") Long saleId){
        SaleCreateResp resp=saleService.showSaleById(saleId);
        return ResponseEntity.status(HttpStatus.OK).body(ResponseApi.success("Sale fetched successfully", resp));
    }
    @PreAuthorize("hasAnyRole('owner', 'customer')")
    @GetMapping
    public ResponseEntity<ResponseApi<Page<SaleCreateResp>>>showAllSale(@RequestParam(defaultValue = "0")int page,
                                                                        @RequestParam(defaultValue = "10")int size){
        Page<SaleCreateResp>saleCreateRespList=saleService.showAllSales(page,size );
        return ResponseEntity.
                status(HttpStatus.OK).
                body(ResponseApi.
                        success("All sales fetched successfully",
                                saleCreateRespList));
    }
}
