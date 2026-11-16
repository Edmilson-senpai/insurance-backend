package com.app.insurance_backend.policy.controller;

import com.app.insurance_backend.policy.dto.PolicyResponse;
import com.app.insurance_backend.policy.dto.ProductsRequest;
import com.app.insurance_backend.policy.dto.PurchasePolicyRequest;
import com.app.insurance_backend.policy.service.PolicyService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/policy")
@AllArgsConstructor
public class PolicyController {
    private final PolicyService policyService;

    @PostMapping("/purchase")
    public ResponseEntity<PolicyResponse> purchasePolicy(
            @RequestBody @Valid PurchasePolicyRequest request) {
        PolicyResponse policyResponse;

        policyResponse = policyService.purchasePolicy(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(policyResponse);
    }

    @GetMapping
    public ResponseEntity<List<PolicyResponse>> getMyPolicies() {
        List<PolicyResponse> policyResponses;

        policyResponses = policyService.getMyPolicies();

        return ResponseEntity.ok(policyResponses);
    }

    @PostMapping("/{policyId}/products")
    public ResponseEntity<PolicyResponse> addProductsToPolicy(
            @PathVariable Integer policyId,
            @RequestBody @Valid ProductsRequest request) {
        PolicyResponse policyResponse;

        policyResponse = policyService.addProductsToPolicy(policyId, request);

        return ResponseEntity.status(HttpStatus.CREATED).body(policyResponse);
    }
}
