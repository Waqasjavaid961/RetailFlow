package com.retailflow.retailflow.service.interfaces;

import com.retailflow.retailflow.dto.request.ProductCreateReq;
import com.retailflow.retailflow.dto.response.ProductCreateResp;
import com.retailflow.retailflow.enums.ProductCategory;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * Service interface defining operations for Product management.
 */
public interface ProductService {
    
    // Creates/adds a new product associated with a store
    ProductCreateResp createProduct(ProductCreateReq req);
    
    // Updates details of an existing product in a specific store
    ProductCreateResp updateProduct(ProductCreateReq req);
    
    // Retrieves a list of all products belonging to a particular category
    Page<ProductCreateResp> showAllProductsCategoryWise(ProductCategory category,int page,int size);
    
    // Retrieves detailed information of a specific product by its name and category
    ProductCreateResp showProductDetails(String name,ProductCategory productCategory);
}
