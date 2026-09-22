package com.app.insurance_backend.insurance.controller;

import com.app.insurance_backend.insurance.dto.InsuranceBenefitRequest;
import com.app.insurance_backend.insurance.dto.InsuranceBenefitResponse;
import com.app.insurance_backend.insurance.dto.InsurancePlanRequest;
import com.app.insurance_backend.insurance.dto.InsurancePlanResponse;
import com.app.insurance_backend.insurance.dto.InsuranceProductRequest;
import com.app.insurance_backend.insurance.dto.InsuranceProductResponse;
import com.app.insurance_backend.insurance.dto.InsuranceRequest;
import com.app.insurance_backend.insurance.dto.InsuranceResponse;
import com.app.insurance_backend.insurance.service.InsuranceService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/insurance")
@AllArgsConstructor
public class InsuranceController {
    private final InsuranceService insuranceService;

    //INSURANCE
    @PostMapping
    public ResponseEntity<InsuranceResponse> createInsurance(
            @RequestBody @Valid InsuranceRequest insuranceRequest) {
        InsuranceResponse insuranceResponse;

        insuranceResponse = insuranceService.createInsurance(insuranceRequest);

        return ResponseEntity.status(HttpStatus.CREATED).body(insuranceResponse);
    }

    @GetMapping
    public ResponseEntity<List<InsuranceResponse>> getAllInsurances() {
        List<InsuranceResponse> insurancesResponse;

        insurancesResponse = insuranceService.getAllInsurances();

        return ResponseEntity.ok(insurancesResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<InsuranceResponse> updateInsurance(
            @PathVariable Integer id,
            @RequestBody @Valid InsuranceRequest insuranceRequest) {
        InsuranceResponse insuranceResponse;

        insuranceResponse = insuranceService.updateInsurance(id, insuranceRequest);

        return ResponseEntity.ok(insuranceResponse);
    }

    //INSURANCE PLAN
    @PostMapping("/{insuranceId}/plans")
    public ResponseEntity<InsurancePlanResponse> createPlan(
            @PathVariable Integer insuranceId,
            @RequestBody @Valid InsurancePlanRequest insurancePlanRequest) {
        InsurancePlanResponse insurancePlanResponse;

        insurancePlanResponse = insuranceService.createPlan(insuranceId, insurancePlanRequest);

        return ResponseEntity.status(HttpStatus.CREATED).body(insurancePlanResponse);
    }

    @GetMapping("/{insuranceId}/plans")
    public ResponseEntity<List<InsurancePlanResponse>> getPlansByInsurance(
            @PathVariable Integer insuranceId) {
        List<InsurancePlanResponse> insurancePlansResponse;

        insurancePlansResponse = insuranceService.getPlansByInsurance(insuranceId);

        return ResponseEntity.ok(insurancePlansResponse);
    }

    @PutMapping("/{insuranceId}/plans/{planId}")
    public ResponseEntity<InsurancePlanResponse> updatePlan(
            @PathVariable Integer insuranceId,
            @PathVariable Integer planId,
            @RequestBody @Valid InsurancePlanRequest insurancePlanRequest) {
        InsurancePlanResponse insurancePlanResponse;

        insurancePlanResponse = insuranceService.updatePlan(planId, insurancePlanRequest);

        return ResponseEntity.ok(insurancePlanResponse);
    }

    //INSURANCE BENEFIT
    @PostMapping("/{insuranceId}/benefits")
    public ResponseEntity<InsuranceBenefitResponse> createBenefit(
            @PathVariable Integer insuranceId,
            @RequestBody @Valid InsuranceBenefitRequest insuranceBenefitRequest) {
        InsuranceBenefitResponse insuranceBenefitResponse;

        insuranceBenefitResponse = insuranceService.createBenefit(insuranceId, insuranceBenefitRequest);

        return ResponseEntity.status(HttpStatus.CREATED).body(insuranceBenefitResponse);
    }

    @GetMapping("/{insuranceId}/benefits")
    public ResponseEntity<List<InsuranceBenefitResponse>> getBenefitsByInsurance(
            @PathVariable Integer insuranceId) {
        List<InsuranceBenefitResponse> insuranceBenefitsResponse;

        insuranceBenefitsResponse = insuranceService.getBenefitsByInsurance(insuranceId);

        return ResponseEntity.ok(insuranceBenefitsResponse);
    }

    @PutMapping("/{insuranceId}/benefits/{benefitId}")
    public ResponseEntity<InsuranceBenefitResponse> updateBenefit(
            @PathVariable Integer insuranceId,
            @PathVariable Integer benefitId,
            @RequestBody @Valid InsuranceBenefitRequest insuranceBenefitRequest) {
        InsuranceBenefitResponse insuranceBenefitResponse;

        insuranceBenefitResponse = insuranceService.updateBenefit(benefitId, insuranceBenefitRequest);

        return ResponseEntity.ok(insuranceBenefitResponse);
    }

    //INSURANCE PRODUCT
    @PostMapping("/{insuranceId}/products")
    public ResponseEntity<InsuranceProductResponse> createProduct(
            @PathVariable Integer insuranceId,
            @RequestBody @Valid InsuranceProductRequest insuranceProductRequest) {
        InsuranceProductResponse insuranceProductResponse;

        insuranceProductResponse = insuranceService.createProduct(insuranceId, insuranceProductRequest);

        return ResponseEntity.status(HttpStatus.CREATED).body(insuranceProductResponse);
    }

    @GetMapping("/{insuranceId}/products")
    public ResponseEntity<List<InsuranceProductResponse>> getProductsByInsurance(
            @PathVariable Integer insuranceId) {
        List<InsuranceProductResponse> insuranceProductsResponse;

        insuranceProductsResponse = insuranceService.getProductsByInsurance(insuranceId);

        return ResponseEntity.ok(insuranceProductsResponse);
    }

    @PutMapping("/{insuranceId}/products/{productId}")
    public ResponseEntity<InsuranceProductResponse> updateProduct(
            @PathVariable Integer insuranceId,
            @PathVariable Integer productId,
            @RequestBody @Valid InsuranceProductRequest insuranceProductRequest) {
        InsuranceProductResponse insuranceProductResponse;

        insuranceProductResponse = insuranceService.updateProduct(productId, insuranceProductRequest);

        return ResponseEntity.ok(insuranceProductResponse);
    }
}
