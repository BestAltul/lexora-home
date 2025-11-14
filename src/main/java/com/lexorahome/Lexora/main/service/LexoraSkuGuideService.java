package com.lexorahome.Lexora.main.service;

import com.lexorahome.Lexora.main.entity.DataSourceFactory;
import com.lexorahome.Lexora.main.entity.DataSource;
import com.lexorahome.Lexora.main.entity.Good;
import com.lexorahome.Lexora.main.utils.ColumnBrandMapping;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

            DataSourceFactory dataSourceFactoryFactory = new DataSourceFactory(goodService,retailService,parserService);
            DataSource sheetSource = dataSourceFactoryFactory.getSource("sheet");

            //1. parse Lexora core data
            List<String> mappedLines = sheetSource.parseLexoraSkuGuide(row);
            Good good = goodService.createGood(mappedLines);

            //2. Based on the template create related SKUs (white label, BM+SKUs)
          //  List<String> mappedLinesNotCore = sheetSource.parseLexoraSkuNotCoreGuide(row);

            Map<String,String> mapHomeDepotUs = ColumnBrandMapping.createMappingForLexoraHomeDepotUs();
            Good goodNotCore = goodService.createNotCoreGood(row,mapHomeDepotUs);

            Map<String,String> mapHomeDepotCa = ColumnBrandMapping.createMappingForLexoraHomeDepotCa();
            Good goodNotCoreUs = goodService.createNotCoreGood(row,mapHomeDepotCa);

            Map<String,String> mapLawes = ColumnBrandMapping.createMappingForLexoraLowes();
            Good goodNotCoreLowes = goodService.createNotCoreGood(row,mapLawes);

            Map<String,String> mapHouzz = ColumnBrandMapping.createMappingForLexoraHouzz();
            Good goodNotCoreHouzz = goodService.createNotCoreGood(row,mapHouzz);

            Map<String,String> mapAmazon = ColumnBrandMapping.createMappingForLexoraAmazon();
            Good goodNotCoreAmazon = goodService.createNotCoreGood(row,mapAmazon);

            Map<String,String> mapWayFair = ColumnBrandMapping.createMappingForLexoraWayfair();
            Good goodNotCoreWayFair = goodService.createNotCoreGood(row,mapWayFair);

            Map<String,String> mapOverStock = ColumnBrandMapping.createMappingForLexoraOverstock();
            Good goodNotCoreOverStock = goodService.createNotCoreGood(row,mapOverStock);

            Map<String,String> mapCanadianTire = ColumnBrandMapping.createMappingForLexoraCanadianTire();
            Good goodNotCoreCanadianTire = goodService.createNotCoreGood(row,mapCanadianTire);

            Map<String,String> mapTheBay = ColumnBrandMapping.createMappingForLexoraTheBay();
            Good goodNotCoreTheBay = goodService.createNotCoreGood(row,mapTheBay);

            Map<String,String> mapBMAmazon = ColumnBrandMapping.createMappingForBellModernAmazon();
            Good goodNotCoreBMAmazon = goodService.createNotCoreGood(row,mapBMAmazon);



        }

        return true;
    }

    public boolean uploadFiles(MultipartFile file, int maxColumns) throws IOException {

        List<List<String>> data = new ArrayList<>();

        try(Workbook workbook = WorkbookFactory.create(file.getInputStream())){

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
