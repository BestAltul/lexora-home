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
@RequestMapping("/api/v3/price-list")
public class PriceListController {

    private final PriceListService priceListService;

    @PostMapping
    public ResponseEntity<?> createPriceList(@RequestParam("file")MultipartFile file) throws IOException {

        if(file.isEmpty()){
            return ResponseEntity.badRequest().body("File is not found");
        }

        boolean loaded = priceListService.uploadFiles(file,"Lowes",25);

        return ResponseEntity.ok("");
    }


//    public ResponseEntity<Map<String,String>> getPriceList(@RequestBody String Brand){
//        priceListService.
//    }
}
