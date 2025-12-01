package com.lexorahome.Lexora.main.picture_service.controller;

import com.lexorahome.Lexora.main.picture_service.dto.PictureRecord;
import com.lexorahome.Lexora.main.picture_service.dto.PictureTypeRecord;
import com.lexorahome.Lexora.main.picture_service.entity.Picture;
import com.lexorahome.Lexora.main.picture_service.service.PictureService;
import com.lexorahome.Lexora.main.picture_service.service.PictureTypeService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@CrossOrigin(origins = "http://localhost:5173")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v3/picture")
public class PictureRestController {
    private final PictureService pictureService;
    private final ModelMapper modelMapper;
    private final PictureTypeService pictureTypeService;

    @PostMapping("/upload")
    public ResponseEntity<?> uploadPicture(@RequestParam("file") MultipartFile file) throws IOException {
        if(file.isEmpty()){
            return ResponseEntity.ok("");
        }

        Picture picture = pictureService.uploadPicture(file);

        return ResponseEntity.ok(modelMapper.map(picture, PictureRecord.class));
    }

    @GetMapping("/types")
    public ResponseEntity<List<PictureTypeRecord>> getAllPictureTypes(){
        List<PictureTypeRecord> pictureTypeRecordList = pictureTypeService.getAllPictureTypeRecord();
        return ResponseEntity.ok(pictureTypeRecordList);
    }

    @GetMapping
    public ResponseEntity<List<PictureRecord>> getAllPictures(){
        List<PictureRecord> pictureRecordList = pictureService.getAllPictureRecords();
        return ResponseEntity.ok(pictureRecordList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PictureRecord> getPictureById(@PathVariable String id){
        PictureRecord pictureRecord = pictureService.findPictureRecordById(id);
        return ResponseEntity.ok(pictureRecord);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updatePictureById(@PathVariable String id){
        //PictureRecord pictureRecord = pictureService.findPictureRecordById(id);
        return ResponseEntity.ok("true");
    }

    @GetMapping("/{id}/pictures")
    public ResponseEntity<List<PictureRecord>> getAllPicturesByGoodId(@PathVariable String id){
        List<PictureRecord> pictureRecordList = pictureService.findAllPictureByGoodId(id);
        return ResponseEntity.ok(pictureRecordList);
    }
}
