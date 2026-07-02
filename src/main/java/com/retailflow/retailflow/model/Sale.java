package com.retailflow.retailflow.model;

import com.retailflow.retailflow.enums.PaymentType;
import com.retailflow.retailflow.enums.SaleStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "sale")
public class Sale {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long saleId;
    private BigDecimal totalAmount;
    private LocalDateTime saleDate;
    @Enumerated(EnumType.STRING)
    private PaymentType paymentType;
    @ManyToOne
    private Store store;
    @Enumerated(EnumType.STRING)
    private SaleStatus status;
    @ManyToOne(cascade = CascadeType.ALL,optional = true)
    private Customer customer; // nullable
    @OneToMany(mappedBy = "sale",cascade = CascadeType.ALL)
    private List<SaleItem> saleItems;

}
