package com.lexorahome.Lexora.main.entity;

import com.lexorahome.Lexora.main.service.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
@Component
public class SheetSourceList implements DataSource {
    private final GoodService goodService;
    private final RetailService retailService;
    private final ParserService parserService;

    @Override
    public void parse(String retail,List<String> row,PriceList priceList) {

        final int IDX_PROMO_MAP = 8;

        List<String> mappedLines = new ArrayList<>();

        if(retail.equalsIgnoreCase("homedepot.com")){
           // return  parseHomeDepotCom(filePath);
        }else if(retail.equalsIgnoreCase("homedepot.ca")){

        }else if(retail.equalsIgnoreCase("wayfair.com")){

        }else if(retail.equalsIgnoreCase("lowes.com")){
             mappedLines = parserService.parseLowes(row);

        }else{

        }

        Good good = goodService.createGood(mappedLines);

        priceList.getGood().add(good);

        Retail cratedRetail = retailService.getOrCreateRetail(retail);
        priceList.setRetail(cratedRetail);

    }

    public List<String> parseLexoraSkuGuide(List<String> row){

        List<String> mappedLines = parserService.parseLexoraSkuGuide(row);
        return mappedLines;
    }

    @Override
    public List<String> parseLexoraSkuNotCoreGuide(List<String> row) {

        List<String> mappedLines = parserService.parseLexoraNotCoreSkuGuide(row);
        return mappedLines;
    }

}
