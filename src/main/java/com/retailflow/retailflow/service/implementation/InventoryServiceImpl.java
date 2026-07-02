package com.retailflow.retailflow.service.implementation;

import com.retailflow.retailflow.dto.request.InventoryUpdateReq;
import com.retailflow.retailflow.dto.response.InventoryUpdateResp;
import com.retailflow.retailflow.exceptions.InventoryNotFoundException;
import com.retailflow.retailflow.exceptions.OutOfStockException;
import com.retailflow.retailflow.exceptions.ProductNotFoundException;
import com.retailflow.retailflow.helperMethods.InventoryHelperMethod;
import com.retailflow.retailflow.helperMethods.StoreHelperMethod;
import com.retailflow.retailflow.mapper.InventoryMapper;
import com.retailflow.retailflow.model.Inventory;
import com.retailflow.retailflow.model.Product;
import com.retailflow.retailflow.model.Store;
import com.retailflow.retailflow.repositery.InventoryRepositery;
import com.retailflow.retailflow.repositery.ProductRepositery;
import com.retailflow.retailflow.service.interfaces.InventoryService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementation of the InventoryService interface.
 * Coordinates stock increases, decreases, and queries for products.
 */
@Service
public class InventoryServiceImpl implements InventoryService {
    private final InventoryRepositery inventoryRepositery;
    private final InventoryMapper inventoryMapper;
    private final InventoryHelperMethod inventoryHelperMethod;

    public InventoryServiceImpl(InventoryRepositery inventoryRepositery, InventoryMapper inventoryMapper, InventoryHelperMethod inventoryHelperMethod) {
        this.inventoryRepositery = inventoryRepositery;
        this.inventoryMapper = inventoryMapper;
        this.inventoryHelperMethod = inventoryHelperMethod;
    }



    /**
     * Increases the stock level of a product.
     */
    @Transactional
    @Override
    public InventoryUpdateResp increaseStock(InventoryUpdateReq req) {

        Inventory inventory=inventoryHelperMethod.getInventoryByProduct(req.getProductId());
        
        // Add new quantity to existing stock
        Long updatedStock=inventory.getStock()+req.getProductQuantity();
        inventory.setStock(updatedStock);
       Inventory savedInventory= inventoryRepositery.save(inventory);
        
        return inventoryMapper.fromEntity(savedInventory);
    }
    
    /**
     * Decreases the stock level of a product.
     * Prevents stock from dropping below zero by throwing OutOfStockException.
     */
    @Transactional
    @Override
    public InventoryUpdateResp decreaseStock(InventoryUpdateReq req) {
        Inventory inventory=inventoryHelperMethod.getInventoryByProduct(req.getProductId());
        
        // Validate sufficient stock is available
        if(inventory.getStock()<req.getProductQuantity()){
            throw new OutOfStockException("current stock is less then to required stock");
        }
        
        // Deduct quantity from stock
        Long updatedStock=inventory.getStock()-req.getProductQuantity();
        inventory.setStock(updatedStock);
       Inventory savedInventory= inventoryRepositery.save(inventory);
        
        return inventoryMapper.fromEntity(savedInventory);
    }

    /**
     * Returns the current stock status of a specific product.
     */
    @Transactional(readOnly = true)
    @Override
    public InventoryUpdateResp showStock(Long productId) {
        Inventory inventory=inventoryHelperMethod.getInventoryByProduct(productId);
        return inventoryMapper.fromEntity(inventory);
    }
}
