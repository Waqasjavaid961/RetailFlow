package com.retailflow.retailflow.helperMethods;

import com.retailflow.retailflow.exceptions.ProductNotFoundException;
import com.retailflow.retailflow.model.Product;
import com.retailflow.retailflow.model.Store;
import com.retailflow.retailflow.repositery.ProductRepositery;
import org.springframework.stereotype.Component;

@Component
public class ProductHelperMethod {
    private final ProductRepositery productRepositery;

    public ProductHelperMethod(ProductRepositery productRepositery) {
        this.productRepositery = productRepositery;
    }
    public Product findProductByStoreAndProductId(Store store, Long productId){
        return productRepositery.findByStoreAndProductId(store,productId).
                orElseThrow(()->new ProductNotFoundException("product not found"));


    }
}
