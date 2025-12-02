package com.lexorahome.Lexora.main.picture_service.dto.short_record;

import com.lexorahome.Lexora.main.picture_service.dto.PictureRecord;
import lombok.Builder;

import java.util.List;

@Builder
public record PictureTypeRecordShort(String name, String short_name) {
}
