package com.retailflow.retailflow.controller;

import com.retailflow.retailflow.common.ContextHolder;
import com.retailflow.retailflow.common.ResponseApi;
import com.retailflow.retailflow.dto.request.StoreCreateReq;
import com.retailflow.retailflow.dto.response.StoreCreateResp;
import com.retailflow.retailflow.jwt.JwtUtils;
import com.retailflow.retailflow.service.interfaces.StoreService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.List;


@RestController
@RequestMapping("/api/stores")
public class StoreController {
    private final StoreService storeService;
    private final JwtUtils jwtUtils;

    // Constructor Injection
    public StoreController(StoreService storeService, JwtUtils jwtUtils) {
        this.storeService = storeService;
        this.jwtUtils = jwtUtils;
    }
    @PreAuthorize("hasRole('owner')")
    @PostMapping("select")
    public ResponseEntity<ResponseApi<StoreCreateResp>>selectStore(@RequestParam String storeName){
        StoreCreateResp resp=storeService.
                selectStore(storeName);
        String email= ContextHolder.getEmail();
        String newToken=jwtUtils.
                generateTokenWithStoreId(email,resp.getId());
        resp.
                setToken(newToken);
        return ResponseEntity.
                status(HttpStatus.OK).
                body(ResponseApi.
                        success("select store successfully",resp));
    }
    @PreAuthorize("hasAnyRole('owner','customer')")
    @PostMapping("createStore")
    public ResponseEntity<ResponseApi<StoreCreateResp>>registerStore(@Valid @RequestBody StoreCreateReq req){
        StoreCreateResp resp=storeService.create(req);
        String email= ContextHolder.getEmail();
        String newToken=jwtUtils.
                generateTokenWithStoreId(email,resp.getId());
        resp.
                setToken(newToken);

        return ResponseEntity.
                status(HttpStatus.CREATED).
                body(ResponseApi.
                        success("created successfully",resp));
    }


    @PreAuthorize("hasRole('owner')")
    @PutMapping("updateStore")
    public ResponseEntity<ResponseApi<StoreCreateResp>>updateStore(@Valid @RequestBody StoreCreateReq req) {
        StoreCreateResp resp = storeService.updateStore(req);
        return ResponseEntity.
                status(HttpStatus.OK).
                body(ResponseApi.
                        success("store update successfully", resp));
    }


    @PreAuthorize("hasRole('owner')")
    @GetMapping("showAllStores")
    public ResponseEntity<ResponseApi<Page<StoreCreateResp>>>showAllStores(@RequestParam(defaultValue = "0") int page,
                                                                           @RequestParam(defaultValue = "10") int size ){
        Page<StoreCreateResp> resps=storeService.showAllStores(page,size);
        return ResponseEntity.
                status(HttpStatus.OK).
                body(ResponseApi.
                        success("Stores fetched successfully",
                                resps));
    }


    @PreAuthorize("hasRole('owner')")
    @GetMapping("showStore")
    public ResponseEntity<ResponseApi<StoreCreateResp>>showStore(){
        StoreCreateResp res=storeService.showStoreDetails();
        return ResponseEntity.
                status(HttpStatus.OK).
                body(ResponseApi.
                        success("show",res));
    }


    @PreAuthorize("hasRole('owner')")
    @DeleteMapping("deleteStore")
    public ResponseEntity<ResponseApi<String>>deleteStore(){
        storeService.deleteStore();
        return ResponseEntity.
                status(HttpStatus.OK).
                body(ResponseApi.
                        success("deleted successfully"));
    }
}
