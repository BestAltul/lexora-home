package com.lexorahome.Lexora.main.utils;

import com.lexorahome.Lexora.main.picture_service.dto.PictureRecord;
import com.lexorahome.Lexora.main.picture_service.entity.Picture;

import java.util.List;

public class PictureMapperCustom {
    public static PictureRecord toPictureRecord(Picture picture){
        return PictureRecord.builder()
                .id(picture.getId())
                .name(picture.getName())
                .notes(picture.getNotes())
                .link(picture.getLink())
                .correct(picture.isCorrect())
                .createdAt(picture.getCreatedAt())
                .good(GoodMapperCustom.toGoodRecord(picture.getGood()))
                .pictureType(PictureTypeMapperCustom.toPictureTypeRecord(picture.getPictureType()))
                .priority(picture.getPriority())
                .pictureStatus(picture.getPictureStatus()).build();
    }

    public static List<PictureRecord> toListPictureRecord(List<Picture> pictureList){
        return pictureList.stream().map(PictureMapperCustom::toPictureRecord).toList();
    }
}
