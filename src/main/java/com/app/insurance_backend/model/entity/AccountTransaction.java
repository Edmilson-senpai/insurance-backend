package com.app.insurance_backend.model.entity;

import com.app.insurance_backend.model.enums.TransactionType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "account_transactions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = "bankAccount")
@EqualsAndHashCode(exclude = "bankAccount")
public class AccountTransaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "account_id", nullable = false)
    private BankAccount bankAccount;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;

    @Column(nullable = false)
    private BigDecimal amount;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private LocalDateTime transactionDate;
}
