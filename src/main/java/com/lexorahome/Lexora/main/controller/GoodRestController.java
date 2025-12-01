package com.lexorahome.Lexora.main.controller;

import com.lexorahome.Lexora.main.dto.GoodRecord;
import com.lexorahome.Lexora.main.picture_service.dto.PictureRecord;
import com.lexorahome.Lexora.main.service.GoodServiceApi;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/{id}")
    public ResponseEntity<GoodRecord> getGoodById(@PathVariable String id){
        GoodRecord goodRecord = goodServiceApi.findGoodRecordById(id);
        return ResponseEntity.ok(goodRecord);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateGood(@PathVariable String id, @RequestBody GoodRecord goodRecord){
        goodServiceApi.updateGood(goodRecord,id);
        return  ResponseEntity.ok("");
    }

}
