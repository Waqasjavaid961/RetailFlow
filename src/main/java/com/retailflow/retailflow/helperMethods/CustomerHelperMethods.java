package com.retailflow.retailflow.helperMethods;

import com.retailflow.retailflow.exceptions.CustomerNotFoundException;
import com.retailflow.retailflow.model.Customer;
import com.retailflow.retailflow.repositery.CustomerRepositery;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
public class CustomerHelperMethods {
    private final CustomerRepositery customerRepositery;

    public CustomerHelperMethods(CustomerRepositery customerRepositery) {
        this.customerRepositery = customerRepositery;
    }

    public  Customer findByContactNumber(String contactNumber){
        return customerRepositery.findByContactNumber(contactNumber).orElseThrow(()->new CustomerNotFoundException("customer not found"));


    }
}
