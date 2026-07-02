package com.retailflow.retailflow.repositery;

import com.retailflow.retailflow.enums.ProductCategory;
import com.retailflow.retailflow.model.Product;
import com.retailflow.retailflow.model.Store;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface ProductRepositery extends JpaRepository<Product,Long> {
    
    boolean existsByStoreAndProductNameAndCategory(Store store,String productName,ProductCategory category);
    
    Optional<Product> findByStoreAndProductNameAndCategory(Store store,String productName,ProductCategory category);
    Optional<Product> findByStoreAndProductId(Store store, Long productId);
    Page<Product> findByStoreAndCategory(Store store, ProductCategory productCategory, Pageable pageable);
    
}
