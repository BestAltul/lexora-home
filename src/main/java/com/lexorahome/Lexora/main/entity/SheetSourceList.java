package com.lexorahome.Lexora.main.entity;

import com.lexorahome.Lexora.main.exception.GoodNotFoundById;
import com.lexorahome.Lexora.main.service.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
            mappedLines = parserService.parseHomeDepotCom(row,"homedepot.com");
        }else if(retail.equalsIgnoreCase("homedepot.ca")){

        }else if(retail.equalsIgnoreCase("wayfair.com")){

        }else if(retail.equalsIgnoreCase("lowes.com")){
             mappedLines = parserService.parseLowes(row);
        }else{

        }

        String sku = mappedLines.get(0);

        //  Good good = goodService.createGood(mappedLines);

        Optional<Good> goodOptional = goodService.findGoodBySku(mappedLines.get(1));

      //  Good good = goodOptional.orElseThrow(()->new GoodNotFoundById("SKU not found "+ sku));
        if(goodOptional.isPresent()){
            priceList.getGood().add(goodOptional.get());

            //Retail createdRetail = retailService.getOrCreateRetail(retail);
            //priceList.setRetail(createdRetail);

            goodService.savePrice(goodOptional.get(), mappedLines.get(9),"Home Depot USA");

        }else{
            System.out.println("SKU not found "+ sku);
        }



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
