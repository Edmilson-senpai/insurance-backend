package com.app.insurance_backend.payment.controller;

import com.app.insurance_backend.payment.dto.PaymentResponse;
import com.app.insurance_backend.payment.service.PaymentService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/payment")
@AllArgsConstructor
public class PaymentController {
    private final PaymentService paymentService;

    @GetMapping
    public ResponseEntity<List<PaymentResponse>> paymentHistory() {
        List<PaymentResponse> paymentResponses;

        paymentResponses = paymentService.paymentHistory();

        return ResponseEntity.ok(paymentResponses);
    }

    @GetMapping("/policy/{policyId}")
    public ResponseEntity<List<PaymentResponse>> paymentPolicyHistory(
            @PathVariable Integer policyId) {
        List<PaymentResponse> paymentResponses;

        paymentResponses = paymentService.paymentPolicyHistory(policyId);

        return ResponseEntity.ok(paymentResponses);
    }
}
