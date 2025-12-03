package com.lexorahome.Lexora.main.service;

import com.lexorahome.Lexora.main.entity.*;
import com.lexorahome.Lexora.main.exception.CoreSkuIsEmptyException;
import com.lexorahome.Lexora.main.repository.GoodRepository;
import com.lexorahome.Lexora.main.repository.GoodsCollectionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GoodService {
    private final GoodRepository goodRepository;
    private final ColorService colorService;
    private final CategoryService categoryService;
    private final ProductTypeService productTypeService;
    private final GoodsCollectionService goodsCollectionService;
    private final RetailService retailService;

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
    public Good createGood(List<String> row) {
        final int IDX_SKU = 0;
        final int IDX_OLD_SKU = 1;
        final int IDX_TITLE = 2;
        final int IDX_COLLECTION = 3;
        final int IDX_CATEGORY = 4;
        final int IDX_COLOR = 5;
        final int IDX_PRODUCT_TYPE = 6;
        final int IDX_KIT_SINGLE = 7;
        final int IDX_UPC = 8;
        final int IDX_CORE_SKU = 9;
        final int IDX_IS_CORE = 10;

        String skuValue = safeGet(row, IDX_SKU);
        if (skuValue.isEmpty()) {
            throw new IllegalArgumentException("SKU cannot be empty");
        }

        Optional<Good> existing = goodRepository.findBySku(skuValue);
        if (existing.isPresent()) {

            return existing.get();

        }

        Good good = new Good();
        good.setSku(skuValue);
        good.setTitle(safeGet(row, IDX_TITLE));

        String oldSku = safeGet(row, IDX_OLD_SKU);
        if (!oldSku.isEmpty()) {
            good.setOldSku(oldSku);
        }

        String isCoreValue = safeGet(row, IDX_IS_CORE);
        if (isCoreValue.equals("true")){
            good.setCore(true);
        }else{
            good.setCore(false);
            String coreSku = safeGet(row,IDX_CORE_SKU);
            if (coreSku.isEmpty()){
                throw new CoreSkuIsEmptyException("coreSku is empty for non-core item "+skuValue);
            }
        }

        String collectionName = safeGet(row, IDX_COLLECTION);
        if (!collectionName.isEmpty()) {
            GoodsCollection goodsCollection = goodsCollectionService.getOrCreateGoodsCollection(collectionName);
            good.setGoodsCollection(goodsCollection);
        }


        String categoryName = safeGet(row, IDX_CATEGORY);
        if (!categoryName.isEmpty()) {
            Category category = categoryService.getOrCreateCategory(categoryName);
            good.setCategory(category);
        }


        String colorName = safeGet(row, IDX_COLOR);
        if (!colorName.isEmpty()) {
            Color color = colorService.getOrCreateColor(colorName);
            good.setColor(color);
        }


        String productTypeName = safeGet(row, IDX_PRODUCT_TYPE);
        if (!productTypeName.isEmpty()) {
            ProductType productType = productTypeService.getOrCreateProductType(productTypeName);
            good.setProductType(productType);
        }

        String kitOrSingleTextValue = safeGet(row, IDX_KIT_SINGLE);
        if (!productTypeName.isEmpty()) {
            KitOrSingle kitOrSingle = KitOrSingle.fromString(kitOrSingleTextValue);
            good.setKitOrSingle(kitOrSingle);
        }

        String upcRaw = safeGet(row, IDX_UPC);
        if (!upcRaw.isEmpty()) {
            try {
                String upcString = new BigDecimal(upcRaw).toPlainString();
                good.setUpc(upcString);
            } catch (NumberFormatException e) {
                good.setUpc(null);
            }
        }

        try{
            return goodRepository.save(good);
        }catch (Exception e){
            throw new RuntimeException("Error in saving the item "+skuValue);
        }
    }

    public GoodsCollection getMappedCollectionFromCoreCollectionOrCreate(String collection, Good coreGood,String brand,String retail){
        Map<String, GoodsCollection> mappedCollectionWithCoreCollection = goodsCollectionService.getAllNonCoreCollection();
        return goodsCollectionService.getOrCreateNotCoreGoodsCollection(collection,coreGood, mappedCollectionWithCoreCollection,brand,retail);
    }

    public Good createNotCoreGood(List<String> row, Map<String,String> mapping) {
        final int IDX_IS_CORE = Integer.parseInt(mapping.get("IDX_IS_CORE"));
        final int IDX_SKU = Integer.parseInt(mapping.get("IDX_CURRENT_SKU"));
       // final int IDX_OLD_SKU = 1;
        final int IDX_TITLE = Integer.parseInt(mapping.get("IDX_TITLE"));
        final int IDX_COLLECTION = Integer.parseInt(mapping.get("IDX_COLLECTION"));
        final int IDX_CATEGORY = Integer.parseInt(mapping.get("IDX_CATEGORY"));
        //final int IDX_COLOR = 5;
        //final int IDX_PRODUCT_TYPE = 6;
        //final int IDX_KIT_SINGLE = 7;
        final int IDX_UPC = Integer.parseInt(mapping.get("IDX_UPC"));
        final int IDX_CORE_SKU = Integer.parseInt(mapping.get("IDX_CORE_SKU"));
        final int IDX_CORE_COLLECTION_SKU = Integer.parseInt(mapping.get("IDX_CORE_COLLECTION"));

        final String VAL_BRAND = mapping.get("VAL_BRAND");
        final String VAL_RETAIL = mapping.get("VAL_RETAIL");

        String skuValue = safeGet(row, IDX_SKU);

        if (skuValue.isEmpty()) {
            //    throw new IllegalArgumentException("SKU cannot be empty");
            return null;
        }

        Retail foundRetail = retailService.getOrCreateRetail(VAL_RETAIL);

        Optional<Good> existing = goodRepository.findBySku(skuValue);
        if (existing.isPresent()) {

            GoodsCollection existingCollection = existing.get().getGoodsCollection();
            if(existingCollection.getRetail()==null){
                existingCollection.setRetail(foundRetail);
                existingCollection.setName(row.get(IDX_COLLECTION));
                if(VAL_BRAND.equals("Lexora")){
                //    existingCollection.setBrand(Brand.LEXORA);
                }else{
               //     existingCollection.setBrand(Brand.BM);
                }
                goodsCollectionService.save(existingCollection);
            }

            return existing.get();
        }

        Good good = new Good();
        good.setSku(skuValue);
        good.setTitle(safeGet(row, IDX_TITLE));

        String isCoreValue = safeGet(row, IDX_IS_CORE);
        if (isCoreValue.equals("yes")){
            good.setCore(true);
        }else{
            good.setCore(false);
            String coreSku = safeGet(row,IDX_CORE_SKU);

            Optional<Good> optionalGood = goodRepository.findBySku(coreSku);

            if (!optionalGood.isEmpty()){
                good.setCoreGood(optionalGood.get());

                String collectionName = safeGet(row, IDX_COLLECTION);
                if (!collectionName.isEmpty()) {
                    GoodsCollection mappedGoodsCollectionWithCore = getMappedCollectionFromCoreCollectionOrCreate(collectionName,optionalGood.get(),VAL_BRAND,VAL_RETAIL);
                    good.setGoodsCollection(mappedGoodsCollectionWithCore);
                }
            }

            if (coreSku.isEmpty()){
                throw new CoreSkuIsEmptyException("coreSku is empty for non-core item "+skuValue);
            }
        }

        String categoryName = safeGet(row, IDX_CATEGORY);
        if (!categoryName.isEmpty()) {
            Category category = categoryService.getOrCreateCategory(categoryName);
            good.setCategory(category);
        }

        String upcRaw = safeGet(row, IDX_UPC);
        if (!upcRaw.isEmpty()) {
            try {
                String upcString = new BigDecimal(upcRaw).toPlainString();
                good.setUpc(upcString);
            } catch (NumberFormatException e) {
                good.setUpc(null);
            }
        }

        try{
            return goodRepository.save(good);
        }catch (Exception e){
            throw new RuntimeException("Error in saving the item "+skuValue);
        }

    }

    private String safeGet(List<String> row, int index) {
        if (row == null || index >= row.size()) {
            return "";
        }
        String value = row.get(index);
        return value == null ? "" : value.trim();
    }

}
