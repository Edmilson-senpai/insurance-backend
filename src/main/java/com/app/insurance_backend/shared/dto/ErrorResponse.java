package com.app.insurance_backend.shared.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ErrorResponse {
    private Integer statusCode;
    private String message;
    private LocalDateTime timestamp;
}
