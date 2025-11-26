package com.lexorahome.Lexora.main.dto;

import lombok.Builder;

import java.util.UUID;

@Builder
public record BrandRecord(UUID id, String name) {
}
