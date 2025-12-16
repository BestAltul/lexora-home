package com.lexorahome.Lexora.main.price_checker.dto;

public record PriceChecker(String price,
                           String price_was,
                           String price_saving,
                           String percetnage_off,
                           Boolean success) {
}
