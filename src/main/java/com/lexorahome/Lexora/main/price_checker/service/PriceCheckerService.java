package com.lexorahome.Lexora.main.price_checker.service;

import com.lexorahome.Lexora.main.dto.GoodRecord;
import com.lexorahome.Lexora.main.entity.Good;
import com.lexorahome.Lexora.main.entity.PriceList;
import com.lexorahome.Lexora.main.entity.RetailPriceListChecker;
import com.lexorahome.Lexora.main.price_checker.dto.PriceChecker;
import com.lexorahome.Lexora.main.price_checker.util.PriceCheckerMapper;
import com.lexorahome.Lexora.main.repository.PriceListRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PriceCheckerService {
    private final WebClient webClient;
    private final PriceListRepository priceListRepository;

    public void getRestData(String sku){

        String apiKey = "";
        String query = sku;
        String urlString = "https://serpapi.com/search.json?engine=home_depot&q="
                + query + "&api_key=" + apiKey;

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

                in.close();
                con.disconnect();

              //  System.out.println(content.toString());

                PriceChecker priceChecker = PriceCheckerMapper.mapFromJson(content.toString());

                if (priceChecker.success()) {
//                    System.out.println("Price: " + priceChecker.price());
//                    System.out.println("Was: " + priceChecker.price_was());
//                    System.out.println("Saving: " + priceChecker.price_saving());
//                    System.out.println("Discount: " + priceChecker.percetnage_off() + "%");

                    RetailPriceListChecker retailPriceListChecker = new RetailPriceListChecker();




                } else {
                    System.out.println("Failed to extract price info.");
                }


            } else {
                System.out.println("Error: HTTP " + status);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getPrice(List<PriceList> priceListList){

        //LVD48SA311
        //getRestData("LVD48SA311");
        for(PriceList priceList: priceListList){

            getRestData(priceList.getSku());

        }

      //  return null;
    }

    public List<PriceList> getAllGoods(){

        List<PriceList> goodList = priceListRepository.findGoodsWithoutCheckerOrderPromoDesc();

        return goodList;
    }


}

