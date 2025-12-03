package com.lexorahome.Lexora.main.picture_service.service;

import com.lexorahome.Lexora.main.entity.Good;
import com.lexorahome.Lexora.main.exception.PictureNotFoundById;
import com.lexorahome.Lexora.main.picture_service.dto.PictureRecord;
import com.lexorahome.Lexora.main.picture_service.dto.in.PictureRequest;
import com.lexorahome.Lexora.main.picture_service.entity.Picture;
import com.lexorahome.Lexora.main.picture_service.entity.PictureStatus;
import com.lexorahome.Lexora.main.picture_service.entity.PictureType;
import com.lexorahome.Lexora.main.picture_service.file_storage.PictureStorage;
import com.lexorahome.Lexora.main.picture_service.repository.PictureRepository;
import com.lexorahome.Lexora.main.repository.GoodRepository;
import com.lexorahome.Lexora.main.repository.PictureTypeRepository;
import com.lexorahome.Lexora.main.utils.PictureMapper;
import com.lexorahome.Lexora.main.utils.PictureMapperCustom;
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
    private final PictureStorage pictureStorage;
    private final PictureMapper pictureMapper;
    private final PictureTypeRepository pictureTypeRepository;
    private final GoodRepository goodRepository;

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
        return PictureMapperCustom.toPictureRecord(picture);
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

        if (file != null && !file.isEmpty()) {
            try {
                String filename = file.getOriginalFilename();
                Path path = Paths.get("uploads/" + filename);
                Files.createDirectories(path.getParent());
                Files.write(path, file.getBytes());

                picture.setLink("/uploads/" + filename);
            } catch (IOException e) {
                throw new RuntimeException("Failed to store file", e);
            }
        } else if (pictureRequest.getLink() != null) {
            picture.setLink(pictureRequest.getLink());
        }

        pictureRepository.save(picture);

        return PictureMapperCustom.toPictureRecord(picture);
    }

}
