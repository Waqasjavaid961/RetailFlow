package com.retailflow.retailflow.service.interfaces;

import com.retailflow.retailflow.dto.request.StoreCreateReq;
import com.retailflow.retailflow.dto.response.StoreCreateResp;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * Service interface defining operations for Store management.
 */
public interface StoreService {
    
    // Registers a new store in the system
    StoreCreateResp create(StoreCreateReq req);
    
    // Updates details of an existing store
    StoreCreateResp updateStore(StoreCreateReq req);
    
    // Retrieves a list of all stores registered under a specific owner's email
    Page<StoreCreateResp> showAllStores(int page, int size);
    
    // Deletes a store based on owner's email and store name
    void deleteStore();
    
    // Fetches detailed information of a specific store
    StoreCreateResp showStoreDetails();

    StoreCreateResp selectStore(String storeName);
}
