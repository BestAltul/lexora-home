package com.lexorahome.Lexora.main.auth_service.dto;

import lombok.Builder;

@Builder
public record RefreshRequestRecord(String refreshToken,String email) {
}
