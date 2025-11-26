package com.lexorahome.Lexora.main.dto;

import lombok.Builder;

import java.util.UUID;

@Builder
public record RetailRecord(UUID id, String name) {
}
