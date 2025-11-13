package com.lexorahome.Lexora.main.service;

import com.lexorahome.Lexora.main.entity.Good;
import com.lexorahome.Lexora.main.entity.PriceList;
import com.lexorahome.Lexora.main.entity.Retail;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ParserService {

    public List<String> parseLowes(List<String> row){
        final int IDX_SKU = 0;
        final int IDX_OLD_SKU = 1;
        final int IDX_TITLE = 5;
        final int IDX_COLLECTION = 6;
        final int IDX_CATEGORY = 3;
        final int IDX_COLOR = 7;
        final int IDX_PRODUCT_TYPE = 4;
        final int IDX_KIT_SINGLE = 10;
        final int IDX_PROMO_MAP = 17;

        List<String> mappedLines = new ArrayList<>();
        mappedLines.add(row.get(IDX_SKU));
        mappedLines.add("");
        mappedLines.add(row.get(IDX_TITLE));
        mappedLines.add(row.get(IDX_COLLECTION));
        mappedLines.add(row.get(IDX_CATEGORY));
        mappedLines.add(row.get(IDX_COLOR));
        mappedLines.add(row.get(IDX_PRODUCT_TYPE));
        mappedLines.add(row.get(IDX_KIT_SINGLE));
        mappedLines.add(row.get(IDX_PROMO_MAP));
        return mappedLines;
    }

    public List<String> parseLexoraSkuGuide(List<String> row){
        final int IDX_SKU = 0;
        final int IDX_OLD_SKU = 1;
        final int IDX_TITLE = 3;
        final int IDX_COLLECTION = 1;
        final int IDX_CATEGORY = 4;
        final int IDX_UPC = 2;
        final int IDX_IS_CORE = 10;

        List<String> mappedLines = new ArrayList<>();
        mappedLines.add(row.get(IDX_SKU));
        mappedLines.add("");
        mappedLines.add(row.get(IDX_TITLE));
        mappedLines.add(row.get(IDX_COLLECTION));
        mappedLines.add(row.get(IDX_CATEGORY));
        mappedLines.add("");
        mappedLines.add("");
        mappedLines.add("");
        mappedLines.add(row.get(IDX_UPC));
        mappedLines.add("");
        mappedLines.add("true");


        return mappedLines;
    }

    public List<String> parseLexoraNotCoreSkuGuide(List<String> row){
        final int IDX_SKU = 9;
        final int IDX_OLD_SKU = 1;
        final int IDX_TITLE = 12;
        final int IDX_COLLECTION = 8;
        final int IDX_CATEGORY = 4;
        final int IDX_UPC = 10;
        final int IDX_CORE_SKU = 11;
        //final int IDX_IS_CORE = 10;

        List<String> mappedLines = new ArrayList<>();
        mappedLines.add(row.get(IDX_SKU));
        mappedLines.add("");
        mappedLines.add(row.get(IDX_TITLE));
        mappedLines.add(row.get(IDX_COLLECTION));
        mappedLines.add(row.get(IDX_CATEGORY));
        mappedLines.add("");
        mappedLines.add("");
        mappedLines.add("");
        mappedLines.add(row.get(IDX_UPC));
        mappedLines.add("");
        mappedLines.add("false");
        mappedLines.add(row.get(0));


        return mappedLines;
    }
}
