package com.lexorahome.Lexora.main.service;

import com.lexorahome.Lexora.main.entity.Color;
import com.lexorahome.Lexora.main.repository.ColorRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ColorService {

    private final ColorRepository colorRepository;

    @Transactional
    public Color getOrCreateColor(String name){
        return colorRepository.findByNameIgnoreCase(name).orElseGet(()->{Color newColor = new Color();newColor.setName(name);return colorRepository.save(newColor);});
    }
}
