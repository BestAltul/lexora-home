package com.lexorahome.Lexora.main.service;

import com.lexorahome.Lexora.main.entity.*;
import com.lexorahome.Lexora.main.repository.GoodRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GoodService {
    private final GoodRepository goodRepository;
    private final ColorService colorService;
    private final CategoryService categoryService;
    private final ProductTypeService productTypeService;
    private final GoodsCollectionService goodsCollectionService;

    // *****to have the template to map
    // 0 SKU,
    // 1 Old_Sku,
    // 2 Title
    // 3 Collection
    // 4 Category
    // 5 Color
    // 6 Product type
    // 7 Kit or single
    // 8 UPC
    // 9 Core_SKU
    // 10 isCore

    public Good createGood(List<String> row){

        String skuValue = row.get(0).trim();

        if (skuValue.isEmpty()) {
            throw new IllegalArgumentException("SKU cannot be empty");
        }

        Optional<Good> existing = goodRepository.findBySku(skuValue);
        if (existing.isPresent()) {
            return existing.get();
        }

        Good good = new Good();
        good.setSku(skuValue);

        //good.setOldSku(1);

        good.setTitle(row.get(2));
        good.setCore(true);

//        CollectionLexora collectionLexora;
//
//        try {
//            collectionLexora = CollectionLexora.valueOf(row.get(3).toUpperCase());
//        } catch (IllegalArgumentException e) {
//            collectionLexora = CollectionLexora.NOT_FOUND;
//        }
//
//        if(collectionLexora!=null){
//            good.setCollectionLexora(collectionLexora);
//        }

        GoodsCollection goodsCollection = goodsCollectionService.getOrCreateGoodsCollection(row.get(3));
        good.setGoodsCollection(goodsCollection);

        Category category = categoryService.getOrCreateCategory(row.get(4));
        good.setCategory(category);

        String upcRaw = row.get(5);

        try {
            String upcString = new BigDecimal(row.get(5)).toPlainString();
            good.setUpc(upcString);
        } catch (NumberFormatException e) {
            good.setUpc(null);
        }

//        Color color = colorService.getOrCreateColor(row.get(5));
//        good.setColor(color);
//
//        ProductType productType = productTypeService.getOrCreateProductType(row.get(6));
//        good.setProductType(productType);
//
//        good.setKitOrSingle(KitOrSingle.fromString(row.get(7)));

        return goodRepository.save(good);
    }
}
