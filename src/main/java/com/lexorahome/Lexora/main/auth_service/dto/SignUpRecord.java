package com.lexorahome.Lexora.main.auth_service.dto;

import lombok.Builder;

import java.time.Instant;

@Builder
public record SignUpRecord(String firstName,
                           String lastName,
                           String email,
                           String password,
                           Instant signUpDate
                           ) {}
