package com.lexorahome.Lexora.main.controller;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xwpf.usermodel.BreakType;
import org.hibernate.type.descriptor.converter.spi.JpaAttributeConverter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
public class SkuuudleReportController{

    @PostMapping("/skuuudle")
    public ResponseEntity<?> uploadSkuuudleReport(@RequestParam("file")MultipartFile file) throws IOException {

        if(file.isEmpty()){
            return ResponseEntity.badRequest().body("File is not found");
        }

        List<List<String>> data = new ArrayList<>();

        try(Workbook workbook = WorkbookFactory.create(file.getInputStream())){
            Sheet sheet = workbook.getSheetAt(0);

            for(Row row : sheet){

                if(row.getRowNum()==0) continue;

                List<String> rowData = new ArrayList<>();

                int maxColumns = 20;

                for(int i=0; i<maxColumns;i++){
                    Cell cell = row.getCell(i,Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                    cell.setCellType(CellType.STRING);
                    rowData.add(cell.getStringCellValue().trim());
                }

                data.add(rowData);
            }
        }



        return ResponseEntity.ok(data);
    }
}
