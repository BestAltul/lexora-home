package com.lexorahome.Lexora.main.picture_service.dto;

import lombok.Builder;

import java.util.List;

@Builder
public record PictureTypeRecord(String name, String short_name, List<PictureRecord> pictures) {
}
