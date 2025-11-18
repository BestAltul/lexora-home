package com.lexorahome.Lexora.main.service;

import com.lexorahome.Lexora.main.entity.pricechecker.SkuuudleReport;
import com.lexorahome.Lexora.main.repository.SkuuudleReportRepository;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SkuuudleReportUpload {
    private final SkuuudleReportRepository skuuudleReportRepository;

    public boolean uploadReport(List<List<String>> data){


        List<SkuuudleReport> skuuudleReports = new ArrayList<>();

        for (List<String> row : data) {
            if (row.get(0).equalsIgnoreCase("Your Product Information") || row.get(0).equalsIgnoreCase("Retailer")) {
                continue;
            }

            if (row.stream().anyMatch(cell -> cell != null && cell.contains("#VALUE!"))) {
                continue;
            }

            try {
//                PriceList price = PriceList.builder()
//                        .name(row.get(0))
//                      //  .sku(row.get(1))
//                      //  .price(Double.parseDouble(row.get(2)))
//                        .build();
                SkuuudleReport skuuudleReport = new SkuuudleReport();
                skuuudleReport.setRetail(row.get(0));
                skuuudleReport.setLexoraSku(row.get(1));
                skuuudleReport.setUpc(row.get(2));
                skuuudleReport.setTitle(row.get(3));
                skuuudleReport.setLexoraCollection(row.get(4));
                skuuudleReport.setCategory(row.get(5));
                skuuudleReport.setRetailerProductId(row.get(6));

                String rawValue = row.get(7);
                if (rawValue != null && !rawValue.trim().isEmpty() && !rawValue.contains("#VALUE!")) {
                    String clean = rawValue.replace(",", "");
                    skuuudleReport.setPromoMap(new BigDecimal(clean));
                }

                String rawValueFinalPrice = row.get(18);
                //if (rawValueFinalPrice != null && !rawValueFinalPrice.trim().isEmpty() && !rawValueFinalPrice.contains("d")) {
                if (rawValueFinalPrice != null && !rawValueFinalPrice.trim().isEmpty()) {
                    String cleanFinalPrice = rawValueFinalPrice.replace(",", "");
                    skuuudleReport.setFinalPrice(new BigDecimal(cleanFinalPrice));
                }

                String dataValue = row.get(20);
                long excelDays = Long.parseLong(dataValue);

                LocalDate date = LocalDate.of(1899,12,30).plusDays(excelDays);

                skuuudleReport.setDataCollected(date);

                skuuudleReport.setLinkToTheSource(row.get(13));
                skuuudleReports.add(skuuudleReport);

            } catch (Exception e) {
                System.out.println("Error in the raw: " + row + " -> " + e.getMessage());
            }
        }

        skuuudleReportRepository.saveAll(skuuudleReports);

        return true;
    }
}
