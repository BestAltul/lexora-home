package com.lexorahome.Lexora.main.picture_service.dto;

import java.util.List;

public record PictureTypeRecord(String name, String short_name, List<PictureRecord> pictures, PictureTypeRecord pictureType) {
}
