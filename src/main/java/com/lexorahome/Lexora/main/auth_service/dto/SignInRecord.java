package com.lexorahome.Lexora.main.auth_service.dto;

import lombok.Builder;

@Builder
public record SignInRecord(String email, String password) {
}
