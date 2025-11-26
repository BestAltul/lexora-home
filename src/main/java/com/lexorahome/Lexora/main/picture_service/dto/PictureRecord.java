package com.lexorahome.Lexora.main.picture_service.dto;

import lombok.Builder;

import java.time.Instant;
import java.util.UUID;

@Builder
public record PictureRecord(UUID id, String name, String link, Instant createdAt) {
}
