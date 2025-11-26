package com.lexorahome.Lexora.main.utils;

import com.lexorahome.Lexora.main.dto.GoodRecord;
import com.lexorahome.Lexora.main.entity.Good;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface GoodMapper {
    GoodRecord toRecord(Good good);
    List<GoodRecord> toRecordList(List<Good> goods);
}
