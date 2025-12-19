package com.lexorahome.Lexora.main.controller;

import com.lexorahome.Lexora.main.service.PriceListService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/api/v3/price-list")
public class PriceListController {

    private final PriceListService priceListService;

    @PostMapping
    public ResponseEntity<?> createPriceList(@RequestParam("file")MultipartFile file) throws IOException {

        if(file.isEmpty()){
            return ResponseEntity.badRequest().body("File is not found");
        }

        boolean loaded = priceListService.uploadFiles(file,"Homedepot",25);

        return ResponseEntity.ok("");
    }

    @PostMapping("/price-updated")
    public ResponseEntity<?> createPriceListByPriceChanged(@RequestParam("file")MultipartFile file) throws IOException {

        if(file.isEmpty()){
            return ResponseEntity.badRequest().body("File is not found");
        }

        boolean loaded = priceListService.uploadFiles(file,"HD",25);

        return ResponseEntity.ok("");
    }

    @PostMapping("/price-updated-difference")
    public ResponseEntity<?> createPriceListByPriceChanged() throws IOException {


        boolean loaded = priceListService.updateDifference();

        return ResponseEntity.ok("");
    }


    @PostMapping("/price-updated-extra")
    public ResponseEntity<?> createPriceListByExtraFile(@RequestParam("file")MultipartFile file) throws IOException {

        if(file.isEmpty()){
            return ResponseEntity.badRequest().body("File is not found");
        }

        boolean loaded = priceListService.updateExtraFile(file,"Sheet1",6);

        return ResponseEntity.ok("");
    }



//    public ResponseEntity<Map<String,String>> getPriceList(@RequestBody String Brand){
//        priceListService.
//    }
}
