package com.lexorahome.Lexora.main.utils;

import com.lexorahome.Lexora.main.dto.GoodRecord;
import com.lexorahome.Lexora.main.entity.Good;
import org.mapstruct.Builder;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(
        componentModel = "spring",
        uses = { ProductTypeMapper.class, PictureMapper.class },
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        builder = @Builder(disableBuilder = true)
)
public interface GoodMapper {
    @Mapping(source = "picture", target = "picture")
    GoodRecord toRecord(Good good);

    List<GoodRecord> toRecordList(List<Good> goods);
}