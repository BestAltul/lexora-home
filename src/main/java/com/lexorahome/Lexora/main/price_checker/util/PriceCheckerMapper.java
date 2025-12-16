package com.lexorahome.Lexora.main.price_checker.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lexorahome.Lexora.main.price_checker.dto.PriceChecker;

public class PriceCheckerMapper {

    public static PriceChecker mapFromJson(String jsonString) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(jsonString);

            JsonNode firstProduct = root.path("products").get(0);

            if (firstProduct.isMissingNode()) {
                return new PriceChecker(null, null, null, null, false);
            }

            String price = firstProduct.path("price").asText(null);
            String priceWas = firstProduct.path("price_was").asText(null);
            String priceSaving = firstProduct.path("price_saving").asText(null);
            String percentageOff = firstProduct.path("percentage_off").asText(null);

            return new PriceChecker(price, priceWas, priceSaving, percentageOff, true);

        } catch (Exception e) {
            e.printStackTrace();
            return new PriceChecker(null, null, null, null, false);
        }
    }

}
