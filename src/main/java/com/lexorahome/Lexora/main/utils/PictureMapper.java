package com.lexorahome.Lexora.main.utils;

import com.lexorahome.Lexora.main.picture_service.dto.PictureRecord;
import com.lexorahome.Lexora.main.picture_service.entity.Picture;
import org.mapstruct.Builder;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        builder = @Builder(disableBuilder = true))
public interface PictureMapper {
    PictureRecord toPictureRecord(Picture picture);
    List<PictureRecord> toPictureList(List<Picture> pictureList);
}
