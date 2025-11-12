package com.lexorahome.Lexora.main.controller;

import com.lexorahome.Lexora.main.service.LexoraSkuGuideService;
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
@RequestMapping("/api/v3/lexora-sku-guide")
public class LexoraSkuGuideRestController {
    private final LexoraSkuGuideService lexoraSkuGuideService;

    @PostMapping
    public ResponseEntity<?> createLexoraSkuGuide(@RequestParam("file") MultipartFile file) throws IOException {

        if(file.isEmpty()){
            return ResponseEntity.badRequest().body("File is not found");
        }

        boolean loaded = lexoraSkuGuideService.uploadFiles(file,"Lowes",25);

        return ResponseEntity.ok("");
    }

}
