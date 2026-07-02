package com.retailflow.retailflow.controller;

import com.retailflow.retailflow.common.ResponseApi;
import com.retailflow.retailflow.dto.request.ProductCreateReq;
import com.retailflow.retailflow.dto.response.ProductCreateResp;
import com.retailflow.retailflow.enums.ProductCategory;
import com.retailflow.retailflow.service.interfaces.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/owner/products") // Base path mapping for product-related endpoints
public class ProductController {

    private final ProductService productService;

    // Constructor Injection
    public ProductController(ProductService productService) {
        this.productService = productService;
    }
    @PostMapping
    public ResponseEntity<ResponseApi<ProductCreateResp>> createProduct(@RequestBody ProductCreateReq req) {
        ProductCreateResp resp = productService.createProduct(req);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ResponseApi.success("Product created successfully", resp));
    }

    @PutMapping("{id}")
    public ResponseEntity<ResponseApi<ProductCreateResp>> updateProduct(@PathVariable Long id,@RequestBody ProductCreateReq req) {
        ProductCreateResp resp = productService.updateProduct(req);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ResponseApi.success("Product updated successfully", resp));
    }
    @GetMapping
    public ResponseEntity<ResponseApi<Page<ProductCreateResp>>> showAllProductsCategoryWise(@RequestParam ProductCategory category,@RequestParam(defaultValue = "0")int page,@RequestParam(defaultValue = "10")int size) {
        Page<ProductCreateResp> resps = productService.showAllProductsCategoryWise(category,page,size);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ResponseApi.
                        success("Category wise products fetched successfully",
                                resps));
    }
    @GetMapping("search")
    public ResponseEntity<ResponseApi<ProductCreateResp>> showProduct(@RequestParam String name, @RequestParam ProductCategory category) {
        ProductCreateResp res = productService.showProductDetails(name, category);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ResponseApi.success("Product details fetched successfully", res));
    }
}
