package com.retailflow.retailflow.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "saleItem")
public class SaleItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // ID auto-increment karne ke liye
    @Column
    private Long saleItemId;
    @Column
    private Long productId;
    @Column
    private String productName;

    @Column
    private Long quantity;

    @Column
    private BigDecimal unitPrice;

    @Column
    private BigDecimal lineTotal;
    @ManyToOne
    private Sale sale;
    @ManyToOne
    private Product product;
}
