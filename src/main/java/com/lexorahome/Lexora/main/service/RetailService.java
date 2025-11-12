package com.lexorahome.Lexora.main.service;

import com.lexorahome.Lexora.main.entity.Retail;
import com.lexorahome.Lexora.main.repository.RetailRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RetailService {
    private final RetailRepository retailRepository;

    @Transactional
    public Retail getOrCreateRetail(String name){
        return retailRepository.findByNameIgnoringCase(name).orElseGet(()->{Retail newRetail = new Retail();newRetail.setName(name);return retailRepository.save(newRetail);});
    }
}
