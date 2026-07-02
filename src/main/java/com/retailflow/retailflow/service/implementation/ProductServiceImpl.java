package com.retailflow.retailflow.service.implementation;

import com.retailflow.retailflow.dto.request.ProductCreateReq;
import com.retailflow.retailflow.dto.response.ProductCreateResp;
import com.retailflow.retailflow.enums.ProductCategory;
import com.retailflow.retailflow.exceptions.CategoryNotFoundException;
import com.retailflow.retailflow.exceptions.ProductAlreadyExistException;
import com.retailflow.retailflow.exceptions.ProductNotFoundException;
import com.retailflow.retailflow.helperMethods.StoreHelperMethod;
import com.retailflow.retailflow.mapper.ProductMapper;
import com.retailflow.retailflow.model.Inventory;
import com.retailflow.retailflow.model.Product;
import com.retailflow.retailflow.model.Store;
import com.retailflow.retailflow.repositery.ProductRepositery;
import com.retailflow.retailflow.service.interfaces.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Implementation of the ProductService interface.
 * Handles product creation, updating details, querying products category-wise, and retrieving details.
 */
@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepositery productRepositery;
    private final ProductMapper productMapper;
    private final StoreHelperMethod storeHelperMethod;

    public ProductServiceImpl(ProductRepositery productRepositery, ProductMapper productMapper, StoreHelperMethod storeHelperMethod) {
        this.productRepositery = productRepositery;
        this.productMapper = productMapper;
        this.storeHelperMethod = storeHelperMethod;
    }


    /**
     * Creates a new product for a specific store.
     * Also initializes its inventory record with the given initial quantity.
     */
    @Transactional
    @Override
    public ProductCreateResp createProduct(ProductCreateReq req) {
        // Validate category
        ProductCategory productCategory=ProductCategory.toString(req.getCategory());
        if(productCategory==null){
            throw new CategoryNotFoundException("category not found");
        }
        Store store =storeHelperMethod.getLoginStore();
        // Ensure no duplicate product exists in the same store with the same category
        if(productRepositery.existsByStoreAndProductNameAndCategory(store,req.getProductName(),productCategory)){// check the existance
            throw new ProductAlreadyExistException("product already exist with same category");
        }
        
        Product product=productMapper.toEntity(req);
        product.setStore(store);
        product.setCategory(productCategory);
        // Initialize inventory record for the product
        Inventory inventory=new Inventory();
        inventory.setStock(req.getProductQuantity()==null ?0L:req.getProductQuantity());
        product.setInventory(inventory);
        inventory.setProduct(product);
        inventory.setStore(store);
        Product savedProduct=productRepositery.save(product);// save product in database


        return productMapper.fromEntity(savedProduct);
    }
    
    /**
     * Updates details of an existing product in a specific store.
     */
    @Transactional
    @Override
    public ProductCreateResp updateProduct(ProductCreateReq req) {
        // Validate category
        ProductCategory productCategory=ProductCategory.toString(req.getCategory().toLowerCase());
        if(productCategory==null){
            throw new CategoryNotFoundException("category not found");
        }
        Store store = storeHelperMethod.getLoginStore();
        Product product=productRepositery.findByStoreAndProductId(store,req.getId()).
                orElseThrow(()->new ProductNotFoundException("product not found"));
        

        productMapper.updateEntityFromReq(req,product);
        productRepositery.save(product);
        return productMapper.fromEntity(product);
    }

    /**
     * Retrieves all products belonging to a particular category.
     */
    @Transactional(readOnly = true)
    @Override
    public Page<ProductCreateResp> showAllProductsCategoryWise(ProductCategory category,int page,int size) {
        Store store=storeHelperMethod.getLoginStore();
        Pageable pageable= PageRequest.of(page,size);

        Page<Product> products=productRepositery.findByStoreAndCategory(store,category,pageable);
        if(products.isEmpty()){
            throw new ProductNotFoundException("products  not exists by this name of category");
        }
        return products.map(productMapper::fromEntity);
    }
    
    /**
     * Retrieves details of a specific product matching the name and category.
     */
    @Transactional(readOnly = true)
    @Override
    public ProductCreateResp showProductDetails(String name, ProductCategory productCategory) {
        Store store=storeHelperMethod.getLoginStore();
        Product product=productRepositery.findByStoreAndProductNameAndCategory(store,name,productCategory).
                orElseThrow(()->new ProductNotFoundException("product not found"));

        return productMapper.fromEntity(product);
    }
}
