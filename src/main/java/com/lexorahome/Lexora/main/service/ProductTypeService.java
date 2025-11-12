package com.lexorahome.Lexora.main.service;

import com.lexorahome.Lexora.main.entity.ProductType;
import com.lexorahome.Lexora.main.repository.ProductTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductTypeService {
    private final ProductTypeRepository productTypeRepository;

    public ProductType getOrCreateProductType(String name){
        return productTypeRepository.findByNameIgnoringCase(name).orElseGet(()->{ProductType newProductType = new ProductType();newProductType.setName(name);return productTypeRepository.save(newProductType);});
    }
}
