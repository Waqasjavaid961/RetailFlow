package com.retailflow.retailflow.model;
import com.retailflow.retailflow.enums.InstallmentStatus;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "installment_plan")
@Data // Lombok: Getters, Setters, toString, equals, and hashCode
@NoArgsConstructor // Lombok: Non-parameterized constructor (Required by JPA)
@AllArgsConstructor
public class InstallmentPlan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column()
    private Long planId;
    @Column()
    private BigDecimal totalAmount;

    @Column()
    private BigDecimal downPayment;

    @Column()
    private BigDecimal remainingAmount;

    @Column()
    private BigDecimal monthlyInstallment;

    @Column()
    private LocalDate startDate;

    @Column()
    private LocalDate endDate;

    @Column()
    @Enumerated(EnumType.STRING)
    private InstallmentStatus status;

    @OneToMany(mappedBy = "installmentPlan")
    private List<InstallmentPayment> payments;
    @ManyToOne
    private Customer customer;
    @OneToOne
    private Sale sale;
    @ManyToOne
    private Store store;
}

