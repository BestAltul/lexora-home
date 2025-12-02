package com.lexorahome.Lexora.main.picture_service.entity;

import lombok.Builder;

public enum PictureStatus {
    UPLOADED ("The picture is uploaded"),
    REVIEW_REQUIRED ("Needs validation"),
    FINALIZED ("Finalized");

    private final String pictureStatus;

    private PictureStatus(String pictureStatus){
        this.pictureStatus = pictureStatus;
    }

    public String getPictureStatus(){
        return pictureStatus;
    }
}
