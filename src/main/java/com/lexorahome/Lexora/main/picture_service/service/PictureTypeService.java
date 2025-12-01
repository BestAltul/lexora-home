package com.lexorahome.Lexora.main.picture_service.service;

import com.lexorahome.Lexora.main.picture_service.dto.PictureTypeRecord;
import com.lexorahome.Lexora.main.picture_service.entity.PictureType;
import com.lexorahome.Lexora.main.repository.PictureTypeRepository;
import com.lexorahome.Lexora.main.utils.PictureTypeMapper;
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

    public List<PictureTypeRecord> getAllPictureTypeRecord(){
        List<PictureType> pictureTypeList = getAllPictureType();
        return pictureTypeMapper.toPictureTypeList(pictureTypeList);
    }
}
