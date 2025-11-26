package com.lexorahome.Lexora.main.picture_service.controller;

import com.lexorahome.Lexora.main.picture_service.dto.PictureRecord;
import com.lexorahome.Lexora.main.picture_service.entity.Picture;
import com.lexorahome.Lexora.main.picture_service.service.PictureService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v3/picture")
public class PictureRestController {
    private PictureService pictureService;
    private ModelMapper modelMapper;

    @PostMapping("/upload")
    public ResponseEntity<?> uploadPicture(@RequestParam("file") MultipartFile file) throws IOException {
        if(file.isEmpty()){
            return ResponseEntity.ok("");
        }

        Picture picture = pictureService.uploadPicture(file);

        return ResponseEntity.ok(modelMapper.map(picture, PictureRecord.class));
    }
}
