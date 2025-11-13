package com.lexorahome.Lexora.main.entity;

import com.lexorahome.Lexora.main.service.GoodService;
import com.lexorahome.Lexora.main.service.ParserService;
import com.lexorahome.Lexora.main.service.RetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataSourceFactory {
    private final GoodService goodService;
    private final RetailService retailService;
    private final ParserService parserService;

    public DataSource getSource(String retail){
        switch(retail.toLowerCase()){
            case "sheet":
                return new SheetSourceList(goodService,retailService,parserService);
            case "odoo":
        //        return new OdooPriceList();
            default:
               throw new IllegalArgumentException("Unknown source type");
        }
    }
}
