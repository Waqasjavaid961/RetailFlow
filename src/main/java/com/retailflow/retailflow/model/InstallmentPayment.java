package com.retailflow.retailflow.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
@Entity
@Table(name = "installment_payment")
@Data // Lombok: Getters, Setters, etc.
@NoArgsConstructor // Lombok: Non-parameterized constructor
@AllArgsConstructor // Lombok: All-argument constructor
public class InstallmentPayment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column()
    private Long paymentId;
    @ManyToOne(fetch = FetchType.EAGER)
    private InstallmentPlan installmentPlan;
    @Column()
    private BigDecimal amountPaid;
    @Column()
    private LocalDate paymentDate;

}
