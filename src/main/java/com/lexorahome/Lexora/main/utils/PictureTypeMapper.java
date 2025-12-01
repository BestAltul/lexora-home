package com.lexorahome.Lexora.main.utils;

import com.lexorahome.Lexora.main.entity.ProductType;
import com.lexorahome.Lexora.main.picture_service.dto.PictureTypeRecord;
import com.lexorahome.Lexora.main.picture_service.entity.PictureType;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PictureTypeMapper {
    PictureTypeRecord toPictureTypeRecord(PictureType pictureType);
    List<PictureTypeRecord> toProductTypeList(List<PictureType> pictureTypeList);
}
