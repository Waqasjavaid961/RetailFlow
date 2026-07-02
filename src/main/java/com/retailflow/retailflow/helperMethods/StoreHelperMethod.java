package com.retailflow.retailflow.helperMethods;
import com.retailflow.retailflow.common.ContextHolder;
import com.retailflow.retailflow.exceptions.StoreNotFoundException;
import com.retailflow.retailflow.model.Store;
import com.retailflow.retailflow.repositery.StoreRepositery;
import org.springframework.stereotype.Component;
@Component
public class StoreHelperMethod {
    private final StoreRepositery storeRepositery;

    public StoreHelperMethod(StoreRepositery storeRepositery) {
        this.storeRepositery = storeRepositery;
    }

    public Store findByStore(){
        String email= ContextHolder.getEmail();
        Store store=storeRepositery.findByEmail(email);
        if(store==null){
            throw new StoreNotFoundException("store not found via this email");
        }
        return store;
    }
 public Store findByStoreName(String name) {
     String email = ContextHolder.getEmail();
     java.util.List<Store> store = storeRepositery.findAllByEmail(email);
     if (store.isEmpty()) {
         throw new StoreNotFoundException("store not found via this email");
     }
     return store.stream().filter(s->s.getStoreName().equals(name)).
             findFirst().
             orElseThrow(()->new StoreNotFoundException("store not found by this name"));
 }
 public Store getLoginStore(){
        Long storeId=ContextHolder.getStoreId();
        if(storeId==null){
            throw new StoreNotFoundException("store not found");
        }
        return storeRepositery.findById(storeId).orElseThrow(()->new StoreNotFoundException("store not found"));
 }



}
