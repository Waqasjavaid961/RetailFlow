package com.retailflow.retailflow.common;

import com.retailflow.retailflow.exceptions.EmailNotFoundException;
import com.retailflow.retailflow.exceptions.StoreNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class ContextHolder {
    public static String getEmail(){
        org.springframework.security.core.Authentication authentication =SecurityContextHolder.getContext().getAuthentication();
        if(authentication==null){
            throw new EmailNotFoundException("email not found");
        }
        return authentication.getName();
    }
    public static  Long getStoreId(){
        Authentication authentication=SecurityContextHolder.getContext().getAuthentication();
        if(authentication==null) {
            throw new StoreNotFoundException("store not found");
        }
            Object storeId=authentication.getCredentials();
            return storeId !=null ?(Long)storeId:null;
        }


}
