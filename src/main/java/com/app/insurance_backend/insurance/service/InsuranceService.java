package com.app.insurance_backend.insurance.service;

import com.app.insurance_backend.insurance.dto.InsuranceBenefitRequest;
import com.app.insurance_backend.insurance.dto.InsuranceBenefitResponse;
import com.app.insurance_backend.insurance.dto.InsurancePlanRequest;
import com.app.insurance_backend.insurance.dto.InsurancePlanResponse;
import com.app.insurance_backend.insurance.dto.InsuranceProductRequest;
import com.app.insurance_backend.insurance.dto.InsuranceProductResponse;
import com.app.insurance_backend.insurance.dto.InsuranceRequest;
import com.app.insurance_backend.insurance.dto.InsuranceResponse;
import com.app.insurance_backend.insurance.repository.InsuranceBenefitRepository;
import com.app.insurance_backend.insurance.repository.InsurancePlanRepository;
import com.app.insurance_backend.insurance.repository.InsuranceProductRepository;
import com.app.insurance_backend.insurance.repository.InsuranceRepository;
import com.app.insurance_backend.model.entity.Insurance;
import com.app.insurance_backend.model.entity.InsuranceBenefit;
import com.app.insurance_backend.model.entity.InsurancePlan;
import com.app.insurance_backend.model.entity.InsuranceProduct;
import com.app.insurance_backend.model.entity.User;
import com.app.insurance_backend.user.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class InsuranceService {
    private final InsuranceRepository insuranceRepository;
    private final InsurancePlanRepository insurancePlanRepository;
    private final InsuranceBenefitRepository insuranceBenefitRepository;
    private final InsuranceProductRepository insuranceProductRepository;
    private final UserRepository userRepository;

    //INSURANCE
    public InsuranceResponse createInsurance(InsuranceRequest request) {
        User user = getAuthenticateUser();
        Insurance insurance = insuranceRepository.save(Insurance.builder()
                .name(request.getName())
                .description(request.getDescription())
                .basePrice(request.getBasePrice())
                .type(request.getType())
                .status(request.getStatus())
                .createdBy(user)
                .build());

        return mapToInsuranceResponse(insurance);
    }

    public List<InsuranceResponse> getAllInsurances() {
        List<Insurance> insurances = insuranceRepository.findAll();

        return insurances.stream()
                .map(this::mapToInsuranceResponse)
                .toList();
    }

    public InsuranceResponse updateInsurance(Integer id, InsuranceRequest request) {
        Insurance insurance = insuranceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Insurance not found")
                );

        insurance.setName(request.getName());
        insurance.setDescription(request.getDescription());
        insurance.setBasePrice(request.getBasePrice());
        insurance.setType(request.getType());
        insurance.setStatus(request.getStatus());

        return mapToInsuranceResponse(insuranceRepository.save(insurance));
    }

    //INSURANCE PLAN
    public InsurancePlanResponse createPlan(Integer id, InsurancePlanRequest request) {
        Insurance insurance = insuranceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Insurance not found")
                );

        InsurancePlan plan = insurancePlanRepository.save(InsurancePlan.builder()
                .insurance(insurance)
                .name(request.getName())
                .description(request.getDescription())
                .price(request.getPrice())
                .type(request.getType())
                .status(request.getStatus())
                .build());

        return mapToPlanResponse(plan);
    }

    public List<InsurancePlanResponse> getPlansByInsurance(Integer insuranceId) {
        List<InsurancePlan> plans = insurancePlanRepository.findByInsurance_Id(insuranceId);

        return plans.stream()
                .map(this::mapToPlanResponse)
                .toList();
    }

    public InsurancePlanResponse updatePlan(Integer planId, InsurancePlanRequest request) {
        InsurancePlan plan = insurancePlanRepository.findById(planId)
                .orElseThrow(() -> new RuntimeException("Plan not found")
                );

        plan.setName(request.getName());
        plan.setDescription(request.getDescription());
        plan.setPrice(request.getPrice());
        plan.setType(request.getType());
        plan.setStatus(request.getStatus());

        return mapToPlanResponse(insurancePlanRepository.save(plan));
    }

    //INSURANCE BENEFIT
    public InsuranceBenefitResponse createBenefit(Integer insuranceId, InsuranceBenefitRequest request) {
        Insurance insurance = insuranceRepository.findById(insuranceId)
                .orElseThrow(() -> new RuntimeException("Insurance not found")
                );

        InsuranceBenefit insuranceBenefit = insuranceBenefitRepository.save(InsuranceBenefit.builder()
                .insurance(insurance)
                .name(request.getName())
                .description(request.getDescription())
                .type(request.getType())
                .status(request.getStatus())
                .build());

        return mapToBenefitResponse(insuranceBenefit);
    }

    public List<InsuranceBenefitResponse> getBenefitsByInsurance(Integer insuranceId) {
        List<InsuranceBenefit> benefits = insuranceBenefitRepository.findByInsurance_Id(insuranceId);

        return benefits.stream()
                .map(this::mapToBenefitResponse)
                .toList();
    }

    public InsuranceBenefitResponse updateBenefit(Integer benefitId, InsuranceBenefitRequest request) {
        InsuranceBenefit benefit = insuranceBenefitRepository.findById(benefitId)
                .orElseThrow(() -> new RuntimeException("Benefit not found")
                );

        benefit.setName(request.getName());
        benefit.setDescription(request.getDescription());
        benefit.setType(request.getType());
        benefit.setStatus(request.getStatus());

        return mapToBenefitResponse(insuranceBenefitRepository.save(benefit));
    }

    //INSURANCE PRODUCT
    public InsuranceProductResponse createProduct(Integer insuranceId, InsuranceProductRequest request) {
        Insurance insurance = insuranceRepository.findById(insuranceId)
                .orElseThrow(() -> new RuntimeException("Insurance not found")
                );

        InsuranceProduct parentProduct = null;
        if (request.getParentProductId() != null) {
            parentProduct = insuranceProductRepository.findById(request.getParentProductId())
                    .orElseThrow(() -> new RuntimeException("Parent product not found")
                    );
        }

        InsuranceProduct product = insuranceProductRepository.save(InsuranceProduct.builder()
                .insurance(insurance)
                .parentProduct(parentProduct)
                .name(request.getName())
                .description(request.getDescription())
                .price(request.getPrice())
                .type(request.getType())
                .status(request.getStatus())
                .build());

        return mapToProductResponse(product);
    }

    public List<InsuranceProductResponse> getProductsByInsurance(Integer insuranceId) {
        List<InsuranceProduct> products = insuranceProductRepository.findByInsurance_Id(insuranceId);

        return products.stream()
                .map(this::mapToProductResponse)
                .toList();
    }

    public InsuranceProductResponse updateProduct(Integer productId, InsuranceProductRequest request) {
        InsuranceProduct product = insuranceProductRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found")
                );

        if (request.getParentProductId() != null) {
            InsuranceProduct parentProduct = insuranceProductRepository.findById(request.getParentProductId())
                    .orElseThrow(() -> new RuntimeException("Parent product not found")
                    );
            product.setParentProduct(parentProduct);
        } else {
            product.setParentProduct(null);
        }

        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setType(request.getType());
        product.setStatus(request.getStatus());

        return mapToProductResponse(insuranceProductRepository.save(product));
    }

    //GENERAL METHODS
    private User getAuthenticateUser() {
        String username = Objects.requireNonNull(SecurityContextHolder.getContext()
                .getAuthentication()).getName();

        return userRepository.findByName(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    //MAPPERS
    private InsuranceResponse mapToInsuranceResponse(Insurance insurance) {
        return InsuranceResponse.builder()
                .name(insurance.getName())
                .description(insurance.getDescription())
                .basePrice(insurance.getBasePrice())
                .type(insurance.getType())
                .status(insurance.getStatus())
                .build();
    }

    private InsurancePlanResponse mapToPlanResponse(InsurancePlan plan) {
        return InsurancePlanResponse.builder()
                .name(plan.getName())
                .description(plan.getDescription())
                .price(plan.getPrice())
                .type(plan.getType())
                .status(plan.getStatus())
                .build();
    }

    private InsuranceBenefitResponse mapToBenefitResponse(InsuranceBenefit benefit) {
        return InsuranceBenefitResponse.builder()
                .id(benefit.getId())
                .name(benefit.getName())
                .description(benefit.getDescription())
                .type(benefit.getType())
                .status(benefit.getStatus())
                .build();
    }

    private InsuranceProductResponse mapToProductResponse(InsuranceProduct product) {
        List<InsuranceProductResponse> subProducts = product.getSubProducts() != null
                ? product.getSubProducts().stream()
                    .map(this::mapToProductResponse)
                    .collect(Collectors.toList())
                : List.of();

        return InsuranceProductResponse.builder()
                .subProducts(subProducts)
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .type(product.getType())
                .status(product.getStatus())
                .build();
    }
}
