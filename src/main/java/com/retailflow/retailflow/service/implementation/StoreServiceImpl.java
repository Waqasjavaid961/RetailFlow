package com.retailflow.retailflow.service.implementation;

import com.retailflow.retailflow.common.ContextHolder;
import com.retailflow.retailflow.dto.request.StoreCreateReq;
import com.retailflow.retailflow.dto.response.StoreCreateResp;
import com.retailflow.retailflow.enums.UserRole;
import com.retailflow.retailflow.exceptions.*;
import com.retailflow.retailflow.helperMethods.StoreHelperMethod;
import com.retailflow.retailflow.jwt.JwtUtils;
import com.retailflow.retailflow.mapper.StoreMapper;
import com.retailflow.retailflow.model.Store;
import com.retailflow.retailflow.model.User;
import com.retailflow.retailflow.repositery.StoreRepositery;
import com.retailflow.retailflow.repositery.UserRepositery;
import com.retailflow.retailflow.service.interfaces.StoreService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Implementation of the StoreService interface.
 * Manages store registration, updates, retrieval, and deletion.
 */
@Service
public class StoreServiceImpl implements StoreService {
    private final StoreRepositery storeRepositery;
    private final StoreMapper storeMapper;
    private final StoreHelperMethod storeHelperMethod;
    private final UserRepositery userRepositery;
    @Value("${store.size}")
    private int storeSize;

    public StoreServiceImpl(StoreRepositery storeRepositery, StoreMapper storeMapper, StoreHelperMethod storeHelperMethod, UserRepositery userRepositery) {
        this.storeRepositery = storeRepositery;
        this.storeMapper = storeMapper;
        this.storeHelperMethod = storeHelperMethod;
        this.userRepositery = userRepositery;
    }



    /**
     * register a new store
     * get email from context holder (we stored it when user logged in)
     * check store name and contact number are not duplicate
     * if user role is customer then change it to owner because now he has a store
     */
    @Transactional
    @Override
    public StoreCreateResp create(StoreCreateReq req) {
        String email=ContextHolder.getEmail();
        if(storeRepositery.existsByEmailAndStoreName(email,req.getStoreName())){
            throw new StoreAlreadyExistException("store already exist");
        }
        if(storeRepositery.existsByContactNumber(req.getContactNumber())){
            throw new DuplicateContactNumberException("contactNumber Already Exists");
        }
        
        User user=userRepositery.findByEmail(email).
                orElseThrow(()->new UserNotFoundException("user not found"));
        List<Store>stores=storeRepositery.findAllByEmail(email);
        if(stores.size()>storeSize){
            throw new StoreExceedException("you already hit the limit of store creation"+storeSize);
        }
        if(user.getRole()== UserRole.customer) {
            user.setRole(UserRole.owner);

            userRepositery.save(user);
        }
        Store store=storeMapper.toEntity(req);
        store.setActive(true);
        store.setEmail(email);
        Store savedStore= storeRepositery.save(store); // register a store in database


        return storeMapper.fromEntity(savedStore);
    }
    
    /**
     * update the store details
     * before updating check that the new contact number is not used by another store
     */
    @Transactional
    @Override
    public StoreCreateResp updateStore(StoreCreateReq req) {
        Store store=storeHelperMethod.getLoginStore();
        if(storeRepositery.existsByContactNumberAndIdNot(req.getContactNumber(),store.getId())){
            throw new DuplicateContactNumberException("invalid contact number");
        }
        storeMapper.updateEntityFromReq(req,store);
       Store updateStore= storeRepositery.save(store);
        return storeMapper.fromEntity(updateStore);
    }

    /** get all stores of the logged in user with pagination */
    @Override
    public Page<StoreCreateResp> showAllStores(int page,int size) {
        Pageable pageable= PageRequest.of(page,size);
        String email=ContextHolder.getEmail();
        Page<Store>stores=storeRepositery.findAllByEmail(email,pageable);
        return stores.map(storeMapper::fromEntity);
    }
    
    /**
     * Deletes a store matching the owner's email and store name.
     */
    @Transactional
    @Override
    public void deleteStore( ) {
        String email=ContextHolder.getEmail();
        Store store=storeHelperMethod.getLoginStore();
            storeRepositery.deleteByEmailAndStoreName(email,store.getStoreName());
            storeRepositery.flush();
            List<Store>stores=storeRepositery.findAllByEmail(email);
            if(stores.isEmpty()){
                User user=userRepositery.findByEmail(email).orElseThrow(()->new UserNotFoundException("user not found"));
                user.setRole(UserRole.customer);
                userRepositery.save(user);
            }
        }

    
    /** show the details of current selected store */
    @Transactional
    @Override
    public StoreCreateResp showStoreDetails() {
        Store store=storeHelperMethod.getLoginStore();

        return storeMapper.fromEntity(store);
    }

    @Override
    public StoreCreateResp selectStore(String storeName) {
        String email=ContextHolder.getEmail();
        List<Store> stores=storeRepositery.findAllByEmail(email);
        if(stores.isEmpty()){
            throw new StoreNotFoundException("stores are not exists by this email");
        }
        if(stores.size()==1){
            Store store=stores.get(0);
            return storeMapper.fromEntity(store);
        }
        Store selectedStore=stores.stream().
                filter(store->store.getStoreName().
                        equals(storeName)).
                findFirst().
                orElseThrow(()->new StoreNotFoundException("store not found with this name"));
        return storeMapper.fromEntity(selectedStore);
    }

}
