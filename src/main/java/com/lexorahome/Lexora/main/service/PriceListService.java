package com.lexorahome.Lexora.main.service;

import com.lexorahome.Lexora.main.entity.DataSourceFactory;
import com.lexorahome.Lexora.main.entity.PriceList;
import com.lexorahome.Lexora.main.entity.DataSource;
import com.lexorahome.Lexora.main.repository.PriceListRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.apache.poi.ss.usermodel.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class PriceListService {
    private final  PriceListRepository priceListRepository;
    private final GoodService goodService;
    private final RetailService retailService;
    private final ParserService parserService;

    public void createPriceList(List<List<String>> data){



        for(List<String> row : data){

            PriceList priceList = new PriceList();

            if(row.get(0).equalsIgnoreCase("SKU") || row.get(0).equalsIgnoreCase("")||row.get(0).equalsIgnoreCase("1.0")){
                continue;
            }

            if(row.stream().anyMatch(cell->cell != null && cell.contains("#VALUE"))){
                continue;
            }

            DataSourceFactory dataSourceFactory = new DataSourceFactory(goodService,retailService,parserService);
            DataSource dataSource = dataSourceFactory.getSource("sheet");
          //  dataSource.parse("homedepot.com",row,priceList);
            dataSource.parse("HD_PRICE_CHANGED",row,priceList);
        }

       // return priceList;
    }


    public boolean uploadFiles(MultipartFile file,String sheetName,int maxColumns) throws IOException {

        List<List<String>> data = new ArrayList<>();

        try(Workbook workbook = WorkbookFactory.create(file.getInputStream())){
            // try to think how to adapt this to all price lists
            Sheet sheet =  workbook.getSheet(sheetName.trim());

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

        createPriceList(data);

        return true;
    }

    private String cellToString(Cell cell) {
        if (cell == null) return "";

        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue().trim();
            case NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    return new SimpleDateFormat("yyyy-MM-dd").format(cell.getDateCellValue());
                } else {
                    double value = cell.getNumericCellValue();
                    if (value == Math.floor(value)) { // целое число
                        return String.valueOf((long) value);
                    } else {
                        return new DecimalFormat("#.######").format(value);
                    }
                }
            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());
            case FORMULA:
                return cellToString(cell.getCachedFormulaResultType() == CellType.NUMERIC ? cell : cell);
            case BLANK:
            default:
                return "";
        }
    }


}
