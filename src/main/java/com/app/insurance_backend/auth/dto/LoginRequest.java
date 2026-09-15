package com.app.insurance_backend.auth.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginRequest {
    @NotBlank(message = "User name is required")
    private String name;

    @NotBlank(message = "Password is required")
    private String password;
}
