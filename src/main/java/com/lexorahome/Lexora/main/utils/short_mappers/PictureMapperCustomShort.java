package com.lexorahome.Lexora.main.utils.short_mappers;

import com.lexorahome.Lexora.main.picture_service.dto.PictureRecord;
import com.lexorahome.Lexora.main.picture_service.dto.short_record.PictureRecordShort;
import com.lexorahome.Lexora.main.picture_service.dto.short_record.PictureTypeRecordShort;
import com.lexorahome.Lexora.main.picture_service.entity.Picture;
import com.lexorahome.Lexora.main.utils.PictureTypeMapperCustom;

import java.util.List;

public class PictureMapperCustomShort {
    public static PictureRecordShort toPictureRecordShort(Picture picture){
        return PictureRecordShort.builder()
                .id(picture.getId())
                .name(picture.getName())
                .notes(picture.getNotes())
                .link(picture.getLink())
                .createdAt(picture.getCreatedAt())
                .correct(picture.isCorrect())
                .pictureType(PictureTypeMapperCustom.toPictureTypeRecord(picture.getPictureType()))
                .priority(picture.getPriority())
                .pictureStatus(picture.getPictureStatus()).build();
    }

    public static List<PictureRecordShort> toListPictureRecord(List<Picture> pictureList){
        return pictureList.stream().map(PictureMapperCustomShort::toPictureRecordShort).toList();
    }
}
