package com.lexorahome.Lexora.main.service;

import com.lexorahome.Lexora.main.entity.PriceList;
import com.lexorahome.Lexora.main.entity.PriceListFactory;
import com.lexorahome.Lexora.main.entity.PriceListSource;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LexoraSkuGuideService {
    private final GoodService goodService;
    private final RetailService retailService;
    private final ParserService parserService;

    public boolean createLexoraSkueGuide(List<List<String>> data){

        for(List<String> row : data){

            if(row.get(0).equalsIgnoreCase("SKU") || row.get(0).equalsIgnoreCase("")||row.get(0).equalsIgnoreCase("1.0")||row.get(0).equalsIgnoreCase("Lexora")){
                continue;
            }

            if(row.stream().anyMatch(cell->cell != null && cell.contains("#VALUE"))){
                continue;
            }

            PriceListFactory priceListFactory = new PriceListFactory(goodService,retailService,parserService);
            PriceListSource priceListSource = priceListFactory.getSource("sheet");
            priceListSource.parseLexoraSkuGuide(row);
        }

        return true;
    }

    public boolean uploadFiles(MultipartFile file, String sheetName, int maxColumns) throws IOException {

        List<List<String>> data = new ArrayList<>();

        try(Workbook workbook = WorkbookFactory.create(file.getInputStream())){
            // try to think how to adapt this to all price lists
            //Sheet sheet =  workbook.getSheet(sheetName.trim());
            Sheet sheet =  workbook.getSheetAt(0);

            if(sheet == null){
                throw new IllegalArgumentException("The sheet "+file.getName()+" is not found");
            }

            for(Row row : sheet){
                if(row.getRowNum() ==0) continue;

                List<String> rowData = new ArrayList<>();
                for(int i = 0;i<maxColumns;i++){
                    rowData.add(cellToString(row.getCell(i)));
                }
                data.add(rowData);
            }
        }

        createLexoraSkueGuide(data);

        return true;
    }

    private String cellToString(Cell cell) {
        if (cell == null) return "";

        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue();
            case NUMERIC:
                return String.valueOf(cell.getNumericCellValue());
            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());
            case FORMULA:
                return cell.getCellFormula();
            case BLANK:
                return "";
            default:
                return "";
        }
    }


}
