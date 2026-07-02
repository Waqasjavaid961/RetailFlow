package com.retailflow.retailflow.repositery;

import com.retailflow.retailflow.model.Store;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface StoreRepositery extends JpaRepository<Store,Long> {
    boolean existsByEmailAndStoreName(String email,String storeName);
    boolean existsByContactNumber(String contactNumber);
    Store findByEmail(String email);
    Page<Store> findAllByEmail(String email, Pageable pageable);
    List<Store> findAllByEmail(String email);
    boolean existsByContactNumberAndIdNot(String contactNumber,Long id);
    void deleteByEmailAndStoreName(String email,String storeName);
    Store findByEmailAndStoreName(String email,String storeName);
}
