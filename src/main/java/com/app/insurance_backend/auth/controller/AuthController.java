package com.app.insurance_backend.auth.controller;

import com.app.insurance_backend.auth.dto.AuthResponse;
import com.app.insurance_backend.auth.dto.LoginRequest;
import com.app.insurance_backend.auth.dto.RegisterRequest;
import com.app.insurance_backend.auth.service.AuthService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@AllArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> setRegisterUser(
            @RequestBody @Valid RegisterRequest registerRequest) {
        AuthResponse authResponse = new AuthResponse();

        authResponse = authService.register(registerRequest);

        return ResponseEntity.ok(authResponse);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> getLoginUser(
            @RequestBody @Valid LoginRequest loginRequest) {
        AuthResponse authResponse = new AuthResponse();

        authResponse = authService.login(loginRequest);

        return ResponseEntity.ok(authResponse);
    }
}
