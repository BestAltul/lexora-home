package com.lexorahome.Lexora.main.utils;

import com.lexorahome.Lexora.main.picture_service.dto.PictureTypeRecord;
import com.lexorahome.Lexora.main.picture_service.entity.PictureType;
import org.mapstruct.Builder;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring",
injectionStrategy = InjectionStrategy.CONSTRUCTOR,
builder = @Builder(disableBuilder = true))
public interface PictureTypeMapper {
    PictureTypeRecord toPictureTypeRecord(PictureType pictureType);
    List<PictureTypeRecord> toPictureTypeList(List<PictureType> pictureTypeList);
}
