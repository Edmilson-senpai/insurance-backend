package com.app.insurance_backend.model.entity;

import com.app.insurance_backend.model.enums.AccountStatus;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "bank_accounts")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = {"user", "transactions"})
@EqualsAndHashCode(exclude = {"user", "transactions"})
public class BankAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false, unique = true)
    private String accountNumber;

    @Column(nullable = false)
    private String bankName;

    @Column(nullable = false)
    private BigDecimal availableBalance;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private AccountStatus status;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime created;

    @OneToMany(mappedBy = "bankAccount", cascade = CascadeType.ALL)
    private List<AccountTransaction> transactions;
}
