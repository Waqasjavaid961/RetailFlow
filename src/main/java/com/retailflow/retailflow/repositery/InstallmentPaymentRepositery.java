package com.retailflow.retailflow.repositery;

import com.retailflow.retailflow.model.InstallmentPayment;
import com.retailflow.retailflow.model.InstallmentPlan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InstallmentPaymentRepositery extends JpaRepository<InstallmentPayment,Long> {
    Page<InstallmentPayment> findByInstallmentPlan(InstallmentPlan installmentPlan, Pageable pageable);

}
