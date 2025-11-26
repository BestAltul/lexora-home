package com.lexorahome.Lexora.main.dto;

import lombok.Builder;

import java.util.UUID;

@Builder
public record ColorRecord(UUID id, String name) {
}
