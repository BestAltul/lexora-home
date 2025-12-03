package com.lexorahome.Lexora.main.picture_service.controller;

import com.lexorahome.Lexora.main.picture_service.dto.PictureRecord;
import com.lexorahome.Lexora.main.picture_service.dto.PictureTypeRecord;
import com.lexorahome.Lexora.main.picture_service.dto.in.PictureRequest;
import com.lexorahome.Lexora.main.picture_service.dto.short_record.PictureRecordShort;
import com.lexorahome.Lexora.main.picture_service.dto.short_record.PictureTypeRecordShort;
import com.lexorahome.Lexora.main.picture_service.entity.Picture;
import com.lexorahome.Lexora.main.picture_service.entity.PictureStatus;
import com.lexorahome.Lexora.main.picture_service.service.PictureService;
import com.lexorahome.Lexora.main.picture_service.service.PictureTypeService;
import com.lexorahome.Lexora.main.service.PictureUploadService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;


@CrossOrigin(origins = "http://localhost:5173")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v3/picture")
public class PictureRestController {
    private final PictureService pictureService;
    private final PictureUploadService pictureUploadService;
    private final ModelMapper modelMapper;
    private final PictureTypeService pictureTypeService;

    @PostMapping("/upload")
    public ResponseEntity<?> uploadPicture(@RequestParam("file") MultipartFile file) throws IOException {
        if(file.isEmpty()){
            return ResponseEntity.ok("");
        }

        Picture picture = pictureUploadService.uploadPicture(file);

        return ResponseEntity.ok(modelMapper.map(picture, PictureRecord.class));
    }

    @GetMapping("/types")
    public ResponseEntity<List<PictureTypeRecordShort>> getAllPictureTypes(){
        List<PictureTypeRecordShort> pictureTypeRecordList = pictureTypeService.getAllPictureTypeRecord();
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
    public ResponseEntity<PictureRecordShort> updatePictureById(@ModelAttribute PictureRequest pictureRequest,@RequestParam(required = false) MultipartFile file){
        PictureRecordShort pictureRecordShort = pictureService.updatePicture(pictureRequest.getPictureId(), pictureRequest,file);
        return ResponseEntity.ok(pictureRecordShort);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<PictureRecordShort> patchPictureById(@ModelAttribute PictureRequest pictureRequest,@RequestParam(required = false) MultipartFile file){
        PictureRecordShort pictureRecordShort = pictureService.updatePicture(pictureRequest.getPictureId(), pictureRequest,file);
        return ResponseEntity.ok(pictureRecordShort);
    }

    @GetMapping("/{id}/pictures")
    public ResponseEntity<List<PictureRecord>> getAllPicturesByGoodId(@PathVariable String id){
        List<PictureRecord> pictureRecordList = pictureService.findAllPictureByGoodId(id);
        return ResponseEntity.ok(pictureRecordList);
    }

    @GetMapping("/statuses")
    public ResponseEntity<PictureStatus[]> getAllStatuses(){
        return ResponseEntity.ok(pictureService.getAllPictureStatuses());
    }

    @PostMapping
    public ResponseEntity<PictureRecord> createPicture(@ModelAttribute PictureRequest pictureRequest, @RequestParam(required = false) MultipartFile file){
        pictureService.createPicture(pictureRequest,file);
        return ResponseEntity.ok(null);
    }
}
