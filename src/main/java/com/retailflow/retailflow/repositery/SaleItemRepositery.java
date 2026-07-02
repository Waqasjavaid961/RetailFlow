package com.retailflow.retailflow.repositery;

import com.retailflow.retailflow.model.SaleItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SaleItemRepositery extends JpaRepository<SaleItem,Long> {
}
