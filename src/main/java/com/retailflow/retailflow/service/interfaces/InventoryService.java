package com.retailflow.retailflow.service.interfaces;

import com.retailflow.retailflow.dto.request.InventoryUpdateReq;
import com.retailflow.retailflow.dto.response.InventoryUpdateResp;
import com.retailflow.retailflow.model.Inventory;

import java.util.Locale;

/**
 * Service interface defining operations for Inventory management.
 */
public interface InventoryService {
    
    // Increases the stock count for a specific product
    InventoryUpdateResp increaseStock(InventoryUpdateReq req);
    
    // Decreases the stock count for a specific product
    InventoryUpdateResp decreaseStock(InventoryUpdateReq req);
    
    // Retrieves current stock details for a specific product
    InventoryUpdateResp showStock(Long productId);
    
}
