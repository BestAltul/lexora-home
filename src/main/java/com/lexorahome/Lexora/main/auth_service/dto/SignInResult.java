package com.lexorahome.Lexora.main.auth_service.dto;

public record SignInResult(PersonRecord personRecord, String refreshToken) {
}
