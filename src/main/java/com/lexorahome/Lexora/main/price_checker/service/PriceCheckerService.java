package com.lexorahome.Lexora.main.price_checker.service;

import com.lexorahome.Lexora.main.dto.GoodRecord;
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

    public List<GoodRecord> getPrice(){

        String apiKey = "";
        String query = "chair";
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

                System.out.println(content.toString());
            } else {
                System.out.println("Error: HTTP " + status);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}

