package com.lexorahome.Lexora.main.service;

import com.lexorahome.Lexora.main.entity.CountertopType;
import com.lexorahome.Lexora.main.repository.CountertopTypeRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CountertopTypeService {
    private final CountertopTypeRepository countertopTypeRepository;

    @Transactional
    public CountertopType getOrCreateCountertopType(String name){
        return countertopTypeRepository.findByNameIgnoringCase(name).orElseGet(()->{CountertopType newCountertopType = new CountertopType();newCountertopType.setName(name);return countertopTypeRepository.save(newCountertopType);});
    }
}
