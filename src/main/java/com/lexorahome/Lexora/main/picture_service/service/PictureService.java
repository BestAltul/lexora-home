package com.lexorahome.Lexora.main.picture_service.service;

import com.lexorahome.Lexora.main.exception.PictureNotFoundById;
import com.lexorahome.Lexora.main.picture_service.dto.PictureRecord;
import com.lexorahome.Lexora.main.picture_service.entity.Picture;
import com.lexorahome.Lexora.main.picture_service.file_storage.PictureStorage;
import com.lexorahome.Lexora.main.picture_service.repository.PictureRepository;
import com.lexorahome.Lexora.main.utils.PictureMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class PictureService {
    private final PictureRepository pictureRepository;
    private final PictureStorage pictureStorage;
    private final PictureMapper pictureMapper;

    public Picture uploadPicture(MultipartFile file) throws IOException {
        String path = pictureStorage.save(file);
        Picture picture = Picture.builder()
                .name(file.getOriginalFilename())
                .link(path)
                .createdAt(Instant.now())
                .modifiedAt(Instant.now())
                .build();
        return pictureRepository.save(picture);
    }

    public List<PictureRecord> getAllPictureRecords(){
        List<Picture> pictureList = pictureRepository.findAll();
        return pictureMapper.toPictureList(pictureList);
    }
    public PictureRecord findPictureRecordById(String id){
        UUID foundId = UUID.fromString(id);
        Picture picture = pictureRepository.findById(foundId).orElseThrow(()->new PictureNotFoundById("Picture not found by ID "+id));
        return pictureMapper.toPictureRecord(picture);
    }

    public List<PictureRecord> findAllPictureByGoodId(String goodId){
        UUID foundId = UUID.fromString(goodId);
        List<Picture> picture = pictureRepository.findAllByGoodId(foundId);

        return pictureMapper.toPictureList(picture);
    }

}
