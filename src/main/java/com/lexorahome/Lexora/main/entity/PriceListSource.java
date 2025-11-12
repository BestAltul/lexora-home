package com.lexorahome.Lexora.main.entity;

import java.util.List;

public interface PriceListSource {
    void parse(String retail,List<String> row,PriceList priceList);
    void parseLexoraSkuGuide(List<String> row);
}
