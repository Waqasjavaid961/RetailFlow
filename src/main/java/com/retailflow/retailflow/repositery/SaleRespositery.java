package com.retailflow.retailflow.repositery;

import com.retailflow.retailflow.model.Customer;
import com.retailflow.retailflow.model.Sale;
import com.retailflow.retailflow.model.Store;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SaleRespositery extends JpaRepository<Sale,Long> {
    Page<Sale> findByStoreId(Long storeId, Pageable pageable);
    Optional<Sale> findBySaleIdAndStore(Long saleId,Store store);
    Sale findByCustomer(Customer customer);
}
