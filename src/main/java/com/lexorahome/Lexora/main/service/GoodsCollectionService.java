package com.lexorahome.Lexora.main.service;

import com.lexorahome.Lexora.main.entity.Brand;
import com.lexorahome.Lexora.main.entity.Good;
import com.lexorahome.Lexora.main.entity.GoodsCollection;
import com.lexorahome.Lexora.main.entity.Retail;
import com.lexorahome.Lexora.main.repository.GoodsCollectionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GoodsCollectionService {
    private final GoodsCollectionRepository goodsCollectionRepository;
    private final RetailService retailService;
    private final Map<String, GoodsCollection> cache = new HashMap<>();


    public GoodsCollection getOrCreateGoodsCollection(String name) {

        if (cache.containsKey(name.toLowerCase())) {
            return cache.get(name.toLowerCase());
        }

        GoodsCollection collection = goodsCollectionRepository.findByNameIgnoreCase(name)
                .orElseGet(() -> {
                    GoodsCollection newCollection = new GoodsCollection();
                    newCollection.setName(name);
                    newCollection.setCore(true);
                    //newCollection.setBrand(Brand.LEXORA);
                    return goodsCollectionRepository.save(newCollection);
                });

        cache.put(name.toLowerCase(), collection);
        return collection;
    }

    public Map<String,GoodsCollection> getAllNonCoreCollection(){
        List<GoodsCollection> goodsCollectionList= goodsCollectionRepository.findAllByIsCoreFalse();
        return goodsCollectionList.stream().collect(Collectors.toMap(GoodsCollection::getName, gc->gc));
    }

    public GoodsCollection getOrCreateNotCoreGoodsCollection(String name, Good coreGood,Map<String,GoodsCollection> mappedCollectionWithCoreCollection,String brand, String retail) {

        if (mappedCollectionWithCoreCollection.containsKey(name)) {
            return mappedCollectionWithCoreCollection.get(name);
        }

        GoodsCollection coreCollection = coreGood.getGoodsCollection();

        GoodsCollection newGoodsCollection = new GoodsCollection();
        newGoodsCollection.setName(name);
        newGoodsCollection.setCoreCollection(coreCollection);
        if(brand.equals("Lexora")){
       //     newGoodsCollection.setBrand(Brand.LEXORA);
        }else{
       //     newGoodsCollection.setBrand(Brand.BM);
        }

        Retail retailCreated = retailService.getOrCreateRetail(retail);
        newGoodsCollection.setRetail(retailCreated);

        GoodsCollection collection = goodsCollectionRepository.save(newGoodsCollection);
        mappedCollectionWithCoreCollection.put(name.toLowerCase(), collection);
        return collection;
    }

    public void save(GoodsCollection goodsCollection){
        goodsCollectionRepository.save(goodsCollection);
    }
}
