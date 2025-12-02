package com.lexorahome.Lexora.main.picture_service.service;

import com.lexorahome.Lexora.main.picture_service.dto.PictureTypeRecord;
import com.lexorahome.Lexora.main.picture_service.dto.short_record.PictureTypeRecordShort;
import com.lexorahome.Lexora.main.picture_service.entity.PictureStatus;
import com.lexorahome.Lexora.main.picture_service.entity.PictureType;
import com.lexorahome.Lexora.main.repository.PictureTypeRepository;
import com.lexorahome.Lexora.main.utils.PictureTypeMapper;
import com.lexorahome.Lexora.main.utils.PictureTypeMapperCustom;
import com.lexorahome.Lexora.main.utils.short_mappers.PictureTypeMapperCustomShort;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class PictureTypeService {
    private final PictureTypeRepository pictureTypeRepository;
    private final ModelMapper modelMapper;
    private final PictureTypeMapper pictureTypeMapper;

    public List<PictureType> getAllPictureType(){
        return pictureTypeRepository.findAll();
    }

    public List<PictureTypeRecordShort> getAllPictureTypeRecord(){
        List<PictureType> pictureTypeList = getAllPictureType();
        return PictureTypeMapperCustomShort.toListPictureTypeShort(pictureTypeList);
    }

}
