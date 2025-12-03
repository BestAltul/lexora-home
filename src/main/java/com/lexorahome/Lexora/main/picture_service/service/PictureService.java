package com.lexorahome.Lexora.main.picture_service.service;

import com.lexorahome.Lexora.main.entity.Good;
import com.lexorahome.Lexora.main.exception.PictureNotFoundById;
import com.lexorahome.Lexora.main.exception.PictureTypeNotFoundByShortName;
import com.lexorahome.Lexora.main.picture_service.dto.PictureRecord;
import com.lexorahome.Lexora.main.picture_service.dto.in.PictureRequest;
import com.lexorahome.Lexora.main.picture_service.dto.short_record.PictureRecordShort;
import com.lexorahome.Lexora.main.picture_service.entity.Picture;
import com.lexorahome.Lexora.main.picture_service.entity.PictureStatus;
import com.lexorahome.Lexora.main.picture_service.entity.PictureType;
import com.lexorahome.Lexora.main.picture_service.repository.PictureRepository;
import com.lexorahome.Lexora.main.repository.GoodRepository;
import com.lexorahome.Lexora.main.repository.PictureTypeRepository;
import com.lexorahome.Lexora.main.utils.PictureMapper;
import com.lexorahome.Lexora.main.utils.PictureMapperCustom;
import com.lexorahome.Lexora.main.utils.short_mappers.PictureMapperCustomShort;
import com.lexorahome.Lexora.main.utils.short_mappers.PictureTypeMapperCustomShort;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class PictureService {
    private final PictureRepository pictureRepository;
    private final PictureMapper pictureMapper;
    private final PictureTypeRepository pictureTypeRepository;
    private final GoodRepository goodRepository;

    public List<PictureRecord> getAllPictureRecords(){
        List<Picture> pictureList = pictureRepository.findAll();
        return PictureMapperCustom.toListPictureRecord(pictureList);
    }
    public PictureRecord findPictureRecordById(String id){
        UUID foundId = UUID.fromString(id);
        Picture picture = pictureRepository.findById(foundId).orElseThrow(()->new PictureNotFoundById("Picture not found by ID "+id));
        return PictureMapperCustom.toPictureRecord(picture);
    }


    private String storeFile(MultipartFile file) {
        if (file == null || file.isEmpty()) return null;

        try {
            String filename = file.getOriginalFilename();
            Path path = Paths.get("uploads/" + filename);
            Files.createDirectories(path.getParent());
            Files.write(path, file.getBytes());
            return "/uploads/" + filename;
        } catch (IOException e) {
            throw new RuntimeException("Failed to store file", e);
        }
    }

    public PictureRecordShort updatePicture(String id, PictureRequest pictureRequest,MultipartFile file){

        UUID foundPictureId = UUID.fromString(id);

        Picture picture = pictureRepository.findById(foundPictureId).orElseThrow(()->new PictureNotFoundById("Picture for updating is not found "+foundPictureId));
        if(pictureRequest.getPriority()!=null){
            picture.setPriority(Integer.parseInt(pictureRequest.getPriority()));
        }
        if(pictureRequest.getNotes()!=null){
            picture.setNotes(pictureRequest.getNotes());
        }
        if(pictureRequest.getPictureStatus()!=null){
            picture.setPictureStatus(PictureStatus.valueOf(pictureRequest.getPictureStatus()));
        }
        if(pictureRequest.getLink()!=null){
            picture.setLink(pictureRequest.getLink());
        }
        if(pictureRequest.getPictureTypeId()!=null){
            PictureType pictureType = pictureTypeRepository.findByShortName(pictureRequest.getPictureTypeId()).orElseThrow(()->new PictureTypeNotFoundByShortName(pictureRequest.getPictureTypeId()));
            picture.setPictureType(pictureType);
        }

        picture.setCorrect(pictureRequest.isCorrect());

        String fileLink = storeFile(file);
        if (fileLink != null) {
            picture.setLink(fileLink);
        } else if (pictureRequest.getLink() != null) {
            picture.setLink(pictureRequest.getLink());
        }

        pictureRepository.save(picture);

        return PictureMapperCustomShort.toPictureRecordShort(picture);
    }

    @Transactional
    public List<PictureRecord> findAllPictureByGoodId(String goodId){
        UUID foundId = UUID.fromString(goodId);
        List<Picture> picture = pictureRepository.findAllByGoodId(foundId);

        return pictureMapper.toPictureList(picture);
    }

    public PictureStatus[] getAllPictureStatuses(){
        return PictureStatus.values();
    }

    public PictureRecord createPicture(PictureRequest pictureRequest,MultipartFile file){

        UUID goodId = UUID.fromString(pictureRequest.getGoodId());
        Good good = goodRepository.findById(goodId)
                .orElseThrow(() -> new RuntimeException("Good not found by id: " + goodId));

        Picture picture = Picture.builder()
                .name(pictureRequest.getName())
                .notes(pictureRequest.getNotes())
                .pictureStatus(PictureStatus.valueOf(pictureRequest.getPictureStatus()))
                .createdAt(Instant.now())

                .modifiedAt(Instant.now())
                .priority(Integer.parseInt(pictureRequest.getPriority())).build();

        pictureTypeRepository.findByShortName(pictureRequest.getPictureTypeId())
                .ifPresent(picture::setPictureType);

        picture.setGood(good);

        String fileLink = storeFile(file);
        if (fileLink != null) {
            picture.setLink(fileLink);
        } else if (pictureRequest.getLink() != null) {
            picture.setLink(pictureRequest.getLink());
        }

        pictureRepository.save(picture);

        return PictureMapperCustom.toPictureRecord(picture);
    }

}
