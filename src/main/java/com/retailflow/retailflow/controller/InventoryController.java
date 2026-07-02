package com.retailflow.retailflow.controller;

import com.retailflow.retailflow.common.ResponseApi;
import com.retailflow.retailflow.dto.request.InventoryUpdateReq;
import com.retailflow.retailflow.dto.response.InventoryUpdateResp;
import com.retailflow.retailflow.service.interfaces.InventoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/owner/inventory") // Base path mapping for inventory-related endpoints
public class InventoryController {

    private final InventoryService inventoryService;

    // Constructor Injection
    public InventoryController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }


    @PutMapping("increase")
    public ResponseEntity<ResponseApi<InventoryUpdateResp>> increaseStock(@RequestBody InventoryUpdateReq req) {
        InventoryUpdateResp resp = inventoryService.increaseStock(req);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ResponseApi.success("Stock increased successfully", resp));
    }


    @PutMapping("decrease")
    public ResponseEntity<ResponseApi<InventoryUpdateResp>> decreaseStock(@RequestBody InventoryUpdateReq req) {
        InventoryUpdateResp resp = inventoryService.decreaseStock(req);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ResponseApi.success("Stock decreased successfully", resp));
    }


    @GetMapping("{productId}")
    public ResponseEntity<ResponseApi<InventoryUpdateResp>> showStock(@PathVariable Long productId) {
        InventoryUpdateResp resp = inventoryService.showStock(productId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ResponseApi.success("Stock details fetched successfully", resp));
    }
}
