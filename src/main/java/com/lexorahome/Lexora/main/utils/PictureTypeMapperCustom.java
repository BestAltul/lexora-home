package com.lexorahome.Lexora.main.utils;

import com.lexorahome.Lexora.main.picture_service.dto.PictureTypeRecord;
import com.lexorahome.Lexora.main.picture_service.dto.short_record.PictureTypeRecordShort;
import com.lexorahome.Lexora.main.picture_service.entity.PictureType;
import com.lexorahome.Lexora.main.utils.short_mappers.PictureMapperCustomShort;

import java.util.List;

public class PictureTypeMapperCustom {
    public static PictureTypeRecord toPictureTypeRecord(PictureType pictureType){

        if(pictureType==null){
            return null;
        }

        return PictureTypeRecord.builder()
                .name(pictureType.getName())
                .short_name(pictureType.getShortName())
                .build();
    }
}
