package com.lexorahome.Lexora.main.service;

import com.lexorahome.Lexora.main.entity.Category;
import com.lexorahome.Lexora.main.repository.CategoryRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;

    @Transactional
    public Category getOrCreateCategory(String name){
        return categoryRepository.findByNameIgnoreCase(name).orElseGet(()->{Category newCategory = new Category();newCategory.setName(name);return categoryRepository.save(newCategory);});
    }
}
