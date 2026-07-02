package com.retailflow.retailflow.helperMethods;

import com.retailflow.retailflow.common.ContextHolder;
import com.retailflow.retailflow.exceptions.InventoryNotFoundException;
import com.retailflow.retailflow.exceptions.ProductNotFoundException;
import com.retailflow.retailflow.exceptions.StoreNotFoundException;
import com.retailflow.retailflow.model.Inventory;
import com.retailflow.retailflow.model.Product;
import com.retailflow.retailflow.model.Store;
import com.retailflow.retailflow.repositery.InventoryRepositery;
import com.retailflow.retailflow.repositery.ProductRepositery;
import com.retailflow.retailflow.repositery.StoreRepositery;
import org.springframework.stereotype.Component;

@Component
public class InventoryHelperMethod {
    private final InventoryRepositery inventoryRepositery;
    private final StoreHelperMethod storeHelperMethod;
    private final ProductRepositery productRepositery;
    private final StoreRepositery storeRepositery;

    public InventoryHelperMethod(InventoryRepositery inventoryRepositery, StoreHelperMethod storeHelperMethod, ProductRepositery productRepositery, StoreRepositery storeRepositery) {
        this.inventoryRepositery = inventoryRepositery;
        this.storeHelperMethod = storeHelperMethod;
        this.productRepositery = productRepositery;
        this.storeRepositery = storeRepositery;
    }
    public Inventory findByProduct(Product product){
        com.retailflow.retailflow.model.Store store =storeHelperMethod.getLoginStore();
        Inventory inventory=inventoryRepositery.findByProductAndStoreId(product,store.getId()).
                orElseThrow(()->new InventoryNotFoundException("inventory not found"));
        if(inventory==null){
            throw new InventoryNotFoundException("inventory not found");
        }
        return inventory;
    }

    public Inventory getInventoryByProduct(Long productId) {
        Long storeId= ContextHolder.getStoreId();
        if(storeId==null){
            throw new StoreNotFoundException("please ensure you login in your store");
        }
        Store store=storeRepositery.findById(storeId).orElseThrow(()->new StoreNotFoundException("store not found"));
        // Find product within the current store
        Product product=productRepositery.findByStoreAndProductId(store,productId).
                orElseThrow(()->new ProductNotFoundException("product not found"));


        // Find associated inventory
        Store store1=storeHelperMethod.getLoginStore();
        if(store1==null){
            throw new StoreNotFoundException("store not found");
        }
        Inventory inventory=inventoryRepositery.findByProductAndStoreId(product,store1.getId()).
                orElseThrow(()->new InventoryNotFoundException("inventory not found"));
        if(inventory==null){
            throw new InventoryNotFoundException("inventory not found");
        }
        return inventory;
    }

}
