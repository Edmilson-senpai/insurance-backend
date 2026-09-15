package com.app.insurance_backend.auth.service;

import com.app.insurance_backend.auth.dto.AuthResponse;
import com.app.insurance_backend.auth.dto.LoginRequest;
import com.app.insurance_backend.auth.dto.RegisterRequest;
import com.app.insurance_backend.auth.security.JwtTokenProvider;
import com.app.insurance_backend.auth.security.UserPrincipal;
import com.app.insurance_backend.model.entity.User;
import com.app.insurance_backend.model.enums.GeneralStatus;
import com.app.insurance_backend.user.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManager authenticationManager;

    public AuthResponse register(RegisterRequest request) {
        User user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .identityDocument(request.getIdentityDocument())
                .phone(request.getPhoneNumber())
                .password(passwordEncoder.encode(request.getPassword()))
                .status(GeneralStatus.ACTIVE)
                .build();

        User registerUser = userRepository.save(user);
        UserDetails userDetails = new UserPrincipal(registerUser);
        String token = jwtTokenProvider.generateToken(userDetails);

        return AuthResponse.builder()
                .token(token)
                .username(registerUser.getName())
                .email(registerUser.getEmail())
                .build();
    }

    public AuthResponse login(LoginRequest request) {
        UserPrincipal loginUser = (UserPrincipal) authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(request.getName(), request.getPassword()))
                .getPrincipal();

        String token = jwtTokenProvider.generateToken(loginUser);

        return AuthResponse.builder()
                .token(token)
                .username(loginUser.getUsername())
                .email(loginUser.getEmail())
                .build();
    }
}
