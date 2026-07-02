package com.retailflow.retailflow.repositery;

import com.retailflow.retailflow.model.Inventory;
import com.retailflow.retailflow.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface InventoryRepositery  extends JpaRepository<Inventory,Long> {
    
    // Finds the inventory record associated with a specific product
    Optional<Inventory> findByProductAndStoreId(Product product,Long storeId);
}
