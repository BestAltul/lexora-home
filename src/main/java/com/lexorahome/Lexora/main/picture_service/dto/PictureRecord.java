package com.lexorahome.Lexora.main.picture_service.dto;

import com.lexorahome.Lexora.main.dto.GoodRecord;
import lombok.Builder;

import java.time.Instant;
import java.util.UUID;

@Builder
public record PictureRecord(UUID id, String name, String notes, String link, Instant createdAt, GoodRecord good) {
}
