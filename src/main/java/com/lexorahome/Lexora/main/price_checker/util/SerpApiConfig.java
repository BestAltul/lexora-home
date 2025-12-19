package com.lexorahome.Lexora.main.price_checker.util;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix="price-checker.serpapi")
public class SerpApiConfig {
    private String apiKey;
    private String deliveryZip;
    private String country;
    private String baseUrl;
}
