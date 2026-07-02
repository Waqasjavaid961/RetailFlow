package com.retailflow.retailflow.model;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

// Inventory entity — tracks stock quantity for each product
// Has a bidirectional one-to-one relationship with Product
@Data
@Entity
@Table(name = "inventory")
public class Inventory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long stock;
    @ManyToOne
    private Store store;

    @OneToOne(mappedBy = "inventory")
    private Product product;
}

