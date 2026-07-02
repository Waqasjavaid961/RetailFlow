package com.retailflow.retailflow.repositery;

import com.retailflow.retailflow.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerRepositery extends JpaRepository<Customer,Long> {
    Optional<Customer> findByContactNumber(String contactNumber);
}
