package com.retailflow.retailflow.common;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

// Base class that other entities (like Store) extend to get audit timestamps automatically
// @MappedSuperclass means JPA will include these fields in child entity tables (not a separate table)
// @EntityListeners hooks into JPA lifecycle events to auto-populate the date fields
@Data
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class BaseEntity {

    // Automatically set when the record is first created — never updated after that
    @CreatedDate
    @Column(updatable = false,nullable = false)
    private LocalDateTime createdAt;

    // Automatically updated every time the record is modified
    @LastModifiedDate
    @Column(updatable = true,nullable = false)
    private LocalDateTime modifiedAt;
}
