package com.lexorahome.Lexora.main.service;

import com.lexorahome.Lexora.main.entity.GoodsCollection;
import com.lexorahome.Lexora.main.repository.GoodsCollectionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GoodsCollectionService {
    private final GoodsCollectionRepository goodsCollectionRepository;
    private final Map<String, GoodsCollection> cache = new HashMap<>();

    public GoodsCollection getOrCreateGoodsCollection(String name) {

        if (cache.containsKey(name.toLowerCase())) {
            return cache.get(name.toLowerCase());
        }

        GoodsCollection collection = goodsCollectionRepository.findByNameIgnoreCase(name)
                .orElseGet(() -> {
                    GoodsCollection newCollection = new GoodsCollection();
                    newCollection.setName(name);
                    return goodsCollectionRepository.save(newCollection);
                });

        cache.put(name.toLowerCase(), collection);
        return collection;
    }
}
