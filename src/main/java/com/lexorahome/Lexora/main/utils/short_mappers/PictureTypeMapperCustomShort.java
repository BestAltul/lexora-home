package com.lexorahome.Lexora.main.utils.short_mappers;

import com.lexorahome.Lexora.main.picture_service.dto.PictureTypeRecord;
import com.lexorahome.Lexora.main.picture_service.dto.short_record.PictureTypeRecordShort;
import com.lexorahome.Lexora.main.picture_service.entity.PictureType;
import com.lexorahome.Lexora.main.utils.PictureMapperCustom;

import java.util.List;

public class PictureTypeMapperCustomShort {
    public static PictureTypeRecordShort toPictureTypeRecordShort(PictureType pictureType){

        if(pictureType==null){
            return null;
        }

        return PictureTypeRecordShort.builder()
                .name(pictureType.getName())
                .short_name(pictureType.getShortName())
                .build();
    }

    public static List<PictureTypeRecordShort> toListPictureTypeShort(List<PictureType> pictureTypeList){
        return pictureTypeList.stream().map(PictureTypeMapperCustomShort::toPictureTypeRecordShort).toList();
    }
}
