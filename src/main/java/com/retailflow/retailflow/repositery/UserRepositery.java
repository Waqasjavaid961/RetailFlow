package com.retailflow.retailflow.repositery;

import com.retailflow.retailflow.model.User;
import org.mapstruct.control.MappingControl;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface UserRepositery extends JpaRepository<User,Long > {
    boolean existsByEmail(String email);
    Optional<User> findByEmail(String email);
}
