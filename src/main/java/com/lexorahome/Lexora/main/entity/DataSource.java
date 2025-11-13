package com.lexorahome.Lexora.main.entity;

import java.util.List;

public interface DataSource {
    void parse(String retail,List<String> row,PriceList priceList);
    List<String> parseLexoraSkuGuide(List<String> row);
    List<String> parseLexoraSkuNotCoreGuide(List<String> row);
}
