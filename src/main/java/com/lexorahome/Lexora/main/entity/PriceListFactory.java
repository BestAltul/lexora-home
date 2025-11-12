package com.lexorahome.Lexora.main.entity;

import com.lexorahome.Lexora.main.service.GoodService;
import com.lexorahome.Lexora.main.service.ParserService;
import com.lexorahome.Lexora.main.service.RetailService;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.core.ApplicationContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PriceListFactory {
    private final GoodService goodService;
    private final RetailService retailService;
    private final ParserService parserService;

    public PriceListSource getSource(String retail){
        switch(retail.toLowerCase()){
            case "sheet":
                return new SheetPriceList(goodService,retailService,parserService);
            case "odoo":
        //        return new OdooPriceList();
            default:
               throw new IllegalArgumentException("Unknown source type");
        }
    }
}
