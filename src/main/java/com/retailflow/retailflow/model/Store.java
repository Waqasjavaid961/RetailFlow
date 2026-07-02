package com.retailflow.retailflow.model;

import com.retailflow.retailflow.common.BaseEntity;
import jakarta.persistence.*;
import jdk.jfr.BooleanFlag;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

// Store entity — maps to the "store" table in the database
// Extends BaseEntity to automatically track createdAt and modifiedAt timestamps
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "store")
public class Store extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String storeName;

    @Column
    private String  contactNumber;

    @Column
    private String email;

    @Column
    private String address;

    // Tracks whether this store is currently active or has been deactivated
    @Column
    @BooleanFlag
    private boolean isActive;

    // One store can have many products; "store" refers to the field name in Product entity
    @OneToMany(mappedBy = "store")
    private List<Product> product;
    @OneToMany(mappedBy = "store")
    private List<Sale>sales;
    @OneToMany(mappedBy = "store")
    private List<InstallmentPlan>installmentPlans;
    // One store can have many inventory records; "store" refers to the field name in Inventory entity
    @OneToMany(mappedBy = "store")
    private List<Inventory>inventories;

}

