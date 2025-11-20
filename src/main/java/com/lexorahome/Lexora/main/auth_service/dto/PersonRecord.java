package com.lexorahome.Lexora.main.auth_service.dto;

import lombok.Builder;

import java.time.Instant;

@Builder
public record PersonRecord (String firstName,
        String lastName,
        String email,
        Instant createdAt,
        Instant lastLogin,
        Instant lockTime,
        Instant refreshTokenExpiration,
        boolean isEmailVerified,
        int failedAttempts){}
