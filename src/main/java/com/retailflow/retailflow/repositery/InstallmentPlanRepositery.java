package com.retailflow.retailflow.repositery;

import com.retailflow.retailflow.model.Customer;
import com.retailflow.retailflow.model.InstallmentPlan;
import com.retailflow.retailflow.model.Sale;
import com.retailflow.retailflow.model.Store;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InstallmentPlanRepositery extends JpaRepository<InstallmentPlan,Long> {
    InstallmentPlan findBySale(Sale sale);
    Page<InstallmentPlan>findByCustomer(Customer customer,Pageable pageable);
    Page<InstallmentPlan> findByStore(Store store, Pageable pageable);

}
