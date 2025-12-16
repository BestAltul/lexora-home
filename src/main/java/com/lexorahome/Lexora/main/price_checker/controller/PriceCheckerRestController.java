package com.lexorahome.Lexora.main.price_checker.controller;

import com.lexorahome.Lexora.main.entity.Good;
import com.lexorahome.Lexora.main.entity.PriceList;
import com.lexorahome.Lexora.main.price_checker.service.PriceCheckerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v3/price-checker")
public class PriceCheckerRestController {
    private final PriceCheckerService priceCheckerService;

    @GetMapping
    public ResponseEntity<?> getAllGoods(){

        List<PriceList> goodList = priceCheckerService.getAllGoods();

        priceCheckerService.getPrice(goodList);

        return ResponseEntity.ok("");
    }
}
