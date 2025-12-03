package com.lexorahome.Lexora.main.picture_service.dto.short_record;

import com.lexorahome.Lexora.main.picture_service.dto.PictureTypeRecord;
import com.lexorahome.Lexora.main.picture_service.entity.PictureStatus;
import lombok.Builder;

import java.time.Instant;
import java.util.UUID;

@Builder
public record PictureRecordShort(UUID id,
                            String name,
                            String notes,
                            String link,
                            boolean correct,
                            Instant createdAt,
                            PictureTypeRecord pictureType,
                            int priority,
                            PictureStatus pictureStatus) {
}
