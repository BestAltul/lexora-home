package com.lexorahome.Lexora.main.price_checker.service;

import com.lexorahome.Lexora.main.entity.Good;
import com.lexorahome.Lexora.main.entity.PriceList;
import com.lexorahome.Lexora.main.entity.RetailPriceListChecker;
import com.lexorahome.Lexora.main.price_checker.dto.PriceChecker;
import com.lexorahome.Lexora.main.price_checker.entity.DeliveryOption;
import com.lexorahome.Lexora.main.price_checker.repository.RetailPriceListCheckerRepository;
import com.lexorahome.Lexora.main.price_checker.util.PriceCheckerMapper;
import com.lexorahome.Lexora.main.price_checker.util.SerpApiConfig;
import com.lexorahome.Lexora.main.repository.PriceListRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.Instant;
import java.util.*;

@Service
@RequiredArgsConstructor
public class PriceCheckerService {
    private final WebClient webClient;
    private final PriceListRepository priceListRepository;
    private final RetailPriceListCheckerRepository retailPriceListCheckerRepository;
    private final SerpApiConfig serpApiConfig;

    public String getRestData(String sku,String country){

        Map<String, String> parameter = new HashMap<>();

        String urlString = serpApiConfig.getBaseUrl() +
                "?engine=home_depot_product" +
                "&product_id=" + sku +
                "&delivery_zip=" + serpApiConfig.getDeliveryZip() +
                "&api_key=" + serpApiConfig.getApiKey();

        try {
            URL url = new URL(urlString);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");

            int status = con.getResponseCode();
            if (status == 200) {
                BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                String inputLine;
                StringBuilder content = new StringBuilder();

                while ((inputLine = in.readLine()) != null) {
                    content.append(inputLine);
                }

                return content.toString();

            } else {
                System.out.println("Error: HTTP " + status);
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void getPrice(List<PriceList> priceListList){

        //LVD48SA311
        //getRestData("LVD48SA311");

//        if(!priceListList.isEmpty()){
//            PriceList firstPriceList = priceListList.get(0);
//            Set<Good> skuSet = firstPriceList.getGood();
//
//            Good firstGood = skuSet.stream().findFirst().orElse(null);
//            String retailItemId = firstGood.getRetailItemId();
//            String country = "us";
//
//           // String content = getRestData(sku,country);
//            String content = getRestData(retailItemId,country);
//
//            createPriceListChecker(content,firstPriceList);
//        }
        String country = "us";

        for (PriceList priceList : priceListList) {
            Set<Good> skuSet = priceList.getGood();

            if (skuSet == null || skuSet.isEmpty()) {
                continue;
            }

            Good firstGood = skuSet.stream().findFirst().orElse(null);
            if (firstGood == null) {
                continue;
            }

//            String retailItemId = firstGood.getRetailItemId();
            // changed for requesting by retail_item_id rom price_list, not from good
            String retailItemId = priceList.getRetailItemId();

            String content = getRestData(retailItemId, country);

            createPriceListChecker(content, priceList);
        }

    }
    private BigDecimal toBigDecimal(String value) {
        return value != null ? new BigDecimal(value) : BigDecimal.ZERO;
    }


    public void createPriceListChecker(String content,PriceList priceList){

        PriceChecker priceChecker = PriceCheckerMapper.mapFromJson(content.toString());
        RetailPriceListChecker retailPriceListChecker = new RetailPriceListChecker();

        if (priceChecker.success()) {
            retailPriceListChecker.setCompanyPrice(priceList.getPromoMap());

            retailPriceListChecker.setPrice(toBigDecimal(priceChecker.price()));
            retailPriceListChecker.setPrice_was(toBigDecimal(priceChecker.price_was()));
            retailPriceListChecker.setPrice_saving(toBigDecimal(priceChecker.price_saving()));
            retailPriceListChecker.setPercentage_off(toBigDecimal(priceChecker.percetnage_off()));
            retailPriceListChecker.setRating(priceChecker.rating());
            retailPriceListChecker.setReviews(priceChecker.reviews());
            retailPriceListChecker.setPromoText(priceChecker.promoText());
            retailPriceListChecker.setZipCode(priceChecker.zipCode());
            retailPriceListChecker.setStockAvailability(priceChecker.stockAvailability());
            retailPriceListChecker.setDeliveryType(priceChecker.deliveryType());

            // delivery options
            List<DeliveryOption> deliveryOptions = priceChecker.deliveryOptionRecordList()
                    .stream()
                    .map(record ->{
                            DeliveryOption option = new DeliveryOption(
                            record.type(),
                            record.title(),
                            record.arrivalTime(),
                            record.bottom(),
                            record.quantity());
                            option.setRetailPriceListChecker(retailPriceListChecker);
                            return option;
                    })
                    .toList();

            retailPriceListChecker.setDeliveryOptionList(deliveryOptions);

            retailPriceListChecker.setNotFound(false);
            retailPriceListChecker.setCheckedDate(Instant.now());
            //retailPriceListChecker.getDeliveryType();
            retailPriceListChecker.setPriceList(priceList);
        } else {
            retailPriceListChecker.setPriceList(priceList);
            retailPriceListChecker.setNotFound(true);
        }

        retailPriceListCheckerRepository.save(retailPriceListChecker);
    }

    public List<PriceList> getAllGoods(){

        Pageable limit100 = PageRequest.of(0, 1000);

        List<PriceList> goodList = priceListRepository.findGoodsWithoutCheckerOrderPromoDesc(limit100);

        return goodList;
    }


}

