package com.lexorahome.Lexora.main.service;

import com.lexorahome.Lexora.main.entity.GoodsCollection;
import com.lexorahome.Lexora.main.repository.GoodsCollectionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GoodsCollectionService {
    private final GoodsCollectionRepository goodsCollectionRepository;

    public GoodsCollection getOrCreateGoodsCollection(String name) {
        return goodsCollectionRepository.findByNameIgnoreCase(name).orElseGet(() -> {
            GoodsCollection newGoodsCollection = new GoodsCollection();
            newGoodsCollection.setName(name);
            return goodsCollectionRepository.save(newGoodsCollection);
        });
    }
}
