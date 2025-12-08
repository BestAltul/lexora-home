package com.lexorahome.Lexora.main.picture_service.dto;

import com.lexorahome.Lexora.main.auth_service.dto.PersonRecord;
import lombok.Builder;

import java.time.Instant;

@Builder
public record UserPictureStatusRecord(String name,
                                      Instant createdAt,
                                      Instant modifiedAt,
                                      String notes,
                                      boolean correct,
                                      PersonRecord person,
                                      PictureRecord pictureRecord) {
}
