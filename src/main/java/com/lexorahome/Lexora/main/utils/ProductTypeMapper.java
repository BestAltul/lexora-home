package com.lexorahome.Lexora.main.utils;

import com.lexorahome.Lexora.main.dto.ProductTypeRecord;
import com.lexorahome.Lexora.main.entity.ProductType;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductTypeMapper {
    ProductTypeRecord toRecord(ProductType type);
}