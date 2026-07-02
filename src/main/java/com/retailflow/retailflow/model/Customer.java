package com.retailflow.retailflow.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "customers")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long customerId;

    @Column
    private String customerName;

    @Column
    private String contactNumber;

    @Column
    private String address;
    @OneToMany(mappedBy = "customer")
    private List<Sale>sales;
    @OneToMany(mappedBy = "customer")
    private List<InstallmentPlan>installmentPlans;
}