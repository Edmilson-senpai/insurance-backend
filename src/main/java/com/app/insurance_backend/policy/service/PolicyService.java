package com.app.insurance_backend.policy.service;

import com.app.insurance_backend.insurance.repository.InsurancePlanRepository;
import com.app.insurance_backend.insurance.repository.InsuranceProductRepository;
import com.app.insurance_backend.insurance.repository.InsuranceRepository;
import com.app.insurance_backend.model.entity.*;
import com.app.insurance_backend.model.enums.*;
import com.app.insurance_backend.payment.repository.PaymentRepository;
import com.app.insurance_backend.policy.dto.PolicyProductResponse;
import com.app.insurance_backend.policy.dto.PolicyResponse;
import com.app.insurance_backend.policy.dto.ProductsRequest;
import com.app.insurance_backend.policy.dto.PurchasePolicyRequest;
import com.app.insurance_backend.policy.repository.PolicyProductRepository;
import com.app.insurance_backend.policy.repository.PolicyRepository;
import com.app.insurance_backend.user.repository.AccountTransactionRepository;
import com.app.insurance_backend.user.repository.BankAccountRepository;
import com.app.insurance_backend.user.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Service
@AllArgsConstructor
public class PolicyService {
    private final PolicyRepository policyRepository;
    private final PolicyProductRepository policyProductRepository;
    private final PaymentRepository paymentRepository;
    private final InsuranceRepository insuranceRepository;
    private final InsurancePlanRepository insurancePlanRepository;
    private final InsuranceProductRepository insuranceProductRepository;
    private final BankAccountRepository bankAccountRepository;
    private final AccountTransactionRepository accountTransactionRepository;
    private final UserRepository userRepository;

    @Transactional
    public PolicyResponse purchasePolicy(PurchasePolicyRequest request) {
        User user = getAuthenticateUser();

        BankAccount account = bankAccountRepository.findById(request.getAccountId())
                .orElseThrow(() -> new RuntimeException("Account not found"));

        if (!account.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("Account does not belong to user");
        }

        Insurance insurance = insuranceRepository.findById(request.getInsuranceId())
                .orElseThrow(() -> new RuntimeException("Insurance not found"));

        if (!insurance.getStatus().equals(GeneralStatus.ACTIVE)) {
            throw new RuntimeException("Insurance is not available");
        }

        InsurancePlan plan = insurancePlanRepository.findById(request.getPlanId())
                .orElseThrow(() -> new RuntimeException("Plan not found"));

        if (!plan.getInsurance().getId().equals(insurance.getId())) {
            throw new RuntimeException("Plan does not belong to this insurance");
        }

        if (!plan.getStatus().equals(GeneralStatus.ACTIVE)) {
            throw new RuntimeException("Plan is not available");
        }

        Optional<Policy> policy = policyRepository.findByUser_IdAndInsurance_IdAndStatus(user.getId(), insurance.getId(), GeneralStatus.ACTIVE);

        if (policy.isPresent()) {
            throw new RuntimeException("User already has an active policy for this insurance");
        }

        BigDecimal total = plan.getPrice();
        List<InsuranceProduct> microProducts = new ArrayList<>();

        if (request.getProductIds() != null) {
            for (Integer productId : request.getProductIds()) {
                InsuranceProduct product = insuranceProductRepository.findById(productId)
                        .orElseThrow(() -> new RuntimeException("Product not found:" + productId));

                if (!product.getInsurance().getId().equals(insurance.getId())) {
                    throw new RuntimeException("Product does not belong to this insurance");
                }

                if (!product.getType().equals(ProductType.MICRO)) {
                    throw new RuntimeException("Product is not a micro-insurance");
                }

                if (!product.getStatus().equals(GeneralStatus.ACTIVE)) {
                    throw new RuntimeException("Product is not available");
                }

                total = total.add(product.getPrice());
                microProducts.add(product);
            }
        }

        if (account.getAvailableBalance().compareTo(total) < 0) {
            throw new RuntimeException("Insufficient balance");
        }

        account.setAvailableBalance(account.getAvailableBalance().subtract(total));
        bankAccountRepository.save(account);

        accountTransactionRepository.save(
                AccountTransaction.builder()
                        .bankAccount(account)
                        .type(TransactionType.PAYMENT)
                        .amount(total)
                        .description("Purchase policy - " + insurance.getDescription())
                        .transactionDate(LocalDateTime.now())
                        .build()
        );

        Policy newPolicy = policyRepository.save(
                Policy.builder()
                        .number(generatePolicyNumber())
                        .user(user)
                        .insurance(insurance)
                        .insurancePlan(plan)
                        .account(account)
                        .status(GeneralStatus.ACTIVE)
                        .start(LocalDate.now())
                        .end(endPlan(plan.getType()))
                        .totalAmount(total)
                        .build()
        );

        List<PolicyProduct> savedProducts = new ArrayList<>();
        for (InsuranceProduct product : microProducts) {
            savedProducts.add(policyProductRepository.save(
              PolicyProduct.builder()
                      .policy(newPolicy)
                      .insuranceProduct(product)
                      .price(product.getPrice())
                      .build()
            ));
        }

        paymentRepository.save(
          Payment.builder()
                  .policy(newPolicy)
                  .account(account)
                  .amount(total)
                  .paymentDate(LocalDateTime.now())
                  .method(PaymentMethod.INTERNAL_ACCOUNT)
                  .status(PaymentStatus.COMPLETED)
                  .build()
        );

        return mapToPolicyResponse(newPolicy, savedProducts);
    }

    public List<PolicyResponse> getMyPolicies() {
        User user = getAuthenticateUser();

        return policyRepository.findByUser_Id(user.getId())
                .stream()
                .map(policy -> mapToPolicyResponse(
                        policy,
                        policyProductRepository.findByPolicy(policy)
                ))
                .toList();
    }

    @Transactional
    public PolicyResponse addProductsToPolicy(Integer policyId, ProductsRequest request) {
        User user = getAuthenticateUser();

        Policy policy = policyRepository.findById(policyId)
                .orElseThrow(() -> new RuntimeException("policy not found"));

        if (!policy.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("Policy does not belong to user");
        }

        if (!policy.getStatus().equals(GeneralStatus.ACTIVE)) {
            throw new RuntimeException("Policy is not active");
        }

        BankAccount account = bankAccountRepository.findById(request.getAccountId())
                .orElseThrow(() -> new RuntimeException("Account not found"));

        if (!account.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("Account does not belong to user");
        }

        BigDecimal total = BigDecimal.ZERO;
        List<InsuranceProduct> microProducts = new ArrayList<>();

        for (Integer productId : request.getProductIds()) {
            InsuranceProduct product = insuranceProductRepository.findById(productId)
                    .orElseThrow(() -> new RuntimeException("Product not found: " + productId));

            if (!product.getInsurance().getId().equals(policy.getInsurance().getId())) {
                throw new RuntimeException("Product does not belong to this insurance");
            }

            if (!product.getType().equals(ProductType.MICRO)) {
                throw new RuntimeException("Product is not a micro-insurance");
            }

            if (!product.getStatus().equals(GeneralStatus.ACTIVE)) {
                throw new RuntimeException("Product is not available");
            }

            if (policyProductRepository.existsByPolicyAndInsuranceProduct(policy, product)) {
                throw new RuntimeException("Product already added to this policy: " + productId);
            }

            total = total.add(product.getPrice());
            microProducts.add(product);
        }

        if (account.getAvailableBalance().compareTo(total) < 0) {
            throw new RuntimeException("Insufficient balance");
        }

        account.setAvailableBalance(account.getAvailableBalance().subtract(total));
        bankAccountRepository.save(account);

        accountTransactionRepository.save(
                AccountTransaction.builder()
                        .bankAccount(account)
                        .type(TransactionType.PAYMENT)
                        .amount(total)
                        .description("Add products to policy " + policy.getNumber())
                        .transactionDate(LocalDateTime.now())
                        .build()
        );

        policy.setTotalAmount(policy.getTotalAmount().add(total));
        policyRepository.save(policy);

        paymentRepository.save(
                Payment.builder()
                        .policy(policy)
                        .account(account)
                        .amount(total)
                        .paymentDate(LocalDateTime.now())
                        .method(PaymentMethod.INTERNAL_ACCOUNT)
                        .status(PaymentStatus.COMPLETED)
                        .build()
        );

        for (InsuranceProduct product : microProducts) {
            policyProductRepository.save(
                    PolicyProduct.builder()
                            .policy(policy)
                            .insuranceProduct(product)
                            .price(product.getPrice())
                            .build()
            );
        }

        List<PolicyProduct> policyProducts = policyProductRepository.findByPolicy(policy);

        return mapToPolicyResponse(policy, policyProducts);
    }

    private String generatePolicyNumber() {
        long count = policyRepository.count() + 1;

        return String.format("POL-%d-%05d", LocalDate.now().getYear(), count);
    }

    private LocalDate endPlan (PlanType type) {
        LocalDate start = LocalDate.now();
        return switch (type) {
            case ANNUAL -> start.plusYears(1);
            case MONTHLY -> start.plusMonths(1);
            case ONE_TIME -> null;
        };
    }

    private PolicyResponse mapToPolicyResponse(Policy policy, List<PolicyProduct> policyProducts) {
        List<PolicyProductResponse> products = policyProducts.stream()
                .map(pp -> PolicyProductResponse.builder()
                        .productId(pp.getInsuranceProduct().getId())
                        .productName(pp.getInsuranceProduct().getName())
                        .price(pp.getPrice())
                        .build())
                .toList();

        return PolicyResponse.builder()
                .id(policy.getId())
                .policyNumber(policy.getNumber())
                .insuranceName(policy.getInsurance().getName())
                .planName(policy.getInsurancePlan().getName())
                .status(policy.getStatus())
                .start(policy.getStart())
                .end(policy.getEnd())
                .totalAmount(policy.getTotalAmount())
                .products(products)
                .build();
    }

    private User getAuthenticateUser() {
        String username = Objects.requireNonNull(SecurityContextHolder.getContext()
                .getAuthentication()).getName();

        return userRepository.findByName(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }
}
