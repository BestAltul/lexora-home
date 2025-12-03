package com.lexorahome.Lexora.main.service;

import com.lexorahome.Lexora.main.picture_service.entity.Picture;
import com.lexorahome.Lexora.main.picture_service.file_storage.PictureStorage;
import com.lexorahome.Lexora.main.picture_service.repository.PictureRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.Instant;

@RequiredArgsConstructor
@Service
public class PictureUploadService {
    private final PictureStorage pictureStorage;
    private final PictureRepository pictureRepository;

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
}
