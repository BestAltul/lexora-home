package com.lexorahome.Lexora.main.price_checker.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lexorahome.Lexora.main.price_checker.dto.PriceChecker;

import java.util.Objects;

public class PriceCheckerMapper {

    private static int stockAvailability;

    public static PriceChecker mapFromJson(String jsonString) {

        String deliveryType = "";

        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(jsonString);

            JsonNode firstProduct = root.path("product_results");

            JsonNode promotion = firstProduct.path("promotion");

            JsonNode searchParametrs = root.path("search_parameters");

            JsonNode fulfillment = firstProduct.path("fulfillment");

            JsonNode deliveryOptions = firstProduct.path("fulfillment").path("options");

            if (deliveryOptions.isArray()) {
                for (JsonNode option : deliveryOptions) {
                    if(!Objects.equals(option.path("title").asText(null), "Not available for this item")){
                        deliveryType = deliveryType + ", "+ option.path("type").asText(null);
                    }
                }
            }


            if (firstProduct.isMissingNode()) {
                return new PriceChecker(null, null, null, null, null,false,null,null,null,null,null);
            }

            String price         = firstProduct.path("price").asText(null);
            String priceWas      = promotion.path("original").asText(null);
            String priceSaving   = promotion.path("save").asText(null);
            String percentageOff = promotion.path("per" +
                    "centage").asText(null);
            String rating        = firstProduct.path("rating").asText(null);
            String reviews       = firstProduct.path("reviews").asText(null);

            return new PriceChecker(price, priceWas, priceSaving, percentageOff, promotion.path("type").asText(null),true,rating,reviews,searchParametrs.path("delivery_zip").asText(null),fulfillment.path("quantity").asText(null),deliveryType);

        } catch (Exception e) {
            e.printStackTrace();
            return new PriceChecker(null, null, null, null, null,false,null,null,null,null,null);
        }
    }

}
