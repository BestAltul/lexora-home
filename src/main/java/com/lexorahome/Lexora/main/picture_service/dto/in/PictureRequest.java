package com.lexorahome.Lexora.main.picture_service.dto.in;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PictureRequest {
    private String name;
    private String priority;
    private String notes;
    private String pictureStatus;
    private String pictureTypeId;
    private String goodId;
    private String link;    
}
