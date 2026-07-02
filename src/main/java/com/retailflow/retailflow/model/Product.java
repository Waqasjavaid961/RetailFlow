package com.retailflow.retailflow.model;

import com.retailflow.retailflow.enums.ProductCategory;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

// Product entity — maps to the "product" table in the database
// Each product belongs to one store and has its own inventory record
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;

    @Column
    @Enumerated(EnumType.STRING)
    private ProductCategory category;

    @Column
    private String productName;

    @Column
    private String modelNumber;

    @Column
    private String description;

    @Column
    private BigDecimal price;

    @OneToOne(cascade = CascadeType.ALL)
    private Inventory inventory;

    @ManyToOne()
    private Store store;



}
