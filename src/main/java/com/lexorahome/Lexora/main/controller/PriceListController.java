package com.lexorahome.Lexora.main.controller;

import com.lexorahome.Lexora.main.service.PriceListService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

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

}
