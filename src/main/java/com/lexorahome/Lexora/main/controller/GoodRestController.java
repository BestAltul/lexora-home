package com.lexorahome.Lexora.main.controller;

import com.lexorahome.Lexora.main.dto.GoodRecord;
import com.lexorahome.Lexora.main.service.GoodServiceApi;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/api/v3/goods")
public class GoodRestController {
    private final GoodServiceApi goodServiceApi;

    @GetMapping
    public ResponseEntity<List<GoodRecord>> getAllGoods(){
        List<GoodRecord> goodRecordList = goodServiceApi.getAllGoodRecords();
        return ResponseEntity.ok(goodRecordList);
    }
}
