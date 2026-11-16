package com.app.insurance_backend.payment.service;

import com.app.insurance_backend.model.entity.Payment;
import com.app.insurance_backend.model.entity.Policy;
import com.app.insurance_backend.model.entity.User;
import com.app.insurance_backend.payment.dto.PaymentResponse;
import com.app.insurance_backend.payment.repository.PaymentRepository;
import com.app.insurance_backend.policy.repository.PolicyRepository;
import com.app.insurance_backend.user.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@AllArgsConstructor
public class PaymentService {
    private final UserRepository userRepository;
    private final PolicyRepository policyRepository;
    private final PaymentRepository paymentRepository;

    public List<PaymentResponse> paymentHistory() {
        User user = getAuthenticateUser();

        return paymentRepository.findByPolicy_User_Id(user.getId())
                .stream()
                .map(this::mapToPaymentResponse)
                .toList();
    }

    public List<PaymentResponse> paymentPolicyHistory(Integer policyId) {
        User user = getAuthenticateUser();

        Policy policy = policyRepository.findById(policyId)
                .orElseThrow(() -> new RuntimeException("policy not found"));

        if (!policy.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("Policy does not belong to user");
        }

        List<Payment> payments = paymentRepository.findByPolicy_Id(policyId);

        return payments.stream()
                .map(this::mapToPaymentResponse)
                .toList();
    }

    private PaymentResponse mapToPaymentResponse(Payment payment) {
        return PaymentResponse.builder()
                .id(payment.getId())
                .policyNumber(payment.getPolicy().getNumber())
                .amount(payment.getAmount())
                .paymentDate(payment.getPaymentDate())
                .method(payment.getMethod())
                .status(payment.getStatus())
                .build();
    }

    private User getAuthenticateUser() {
        String username = Objects.requireNonNull(SecurityContextHolder.getContext()
                .getAuthentication()).getName();

        return userRepository.findByName(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }
}
