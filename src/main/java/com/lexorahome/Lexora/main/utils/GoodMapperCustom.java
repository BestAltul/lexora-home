package com.lexorahome.Lexora.main.utils;


import com.lexorahome.Lexora.main.dto.GoodRecord;
import com.lexorahome.Lexora.main.entity.Good;
import com.lexorahome.Lexora.main.utils.short_mappers.PictureMapperCustomShort;

import java.util.List;

public class GoodMapperCustom {

    public static GoodRecord toGoodRecord(Good good) {
        if(good==null){
            return null;
        }
        return GoodRecord.builder()
                .isCore(good.isCore())
                .sku(good.getSku())
                .title(good.getTitle())
                .id(good.getId())
                .upc(good.getUpc())
                .picture(PictureMapperCustomShort.toListPictureRecord(good.getPicture()))
                .kitOrSingle(good.getKitOrSingle()).build();
    }

    public static List<GoodRecord> toListGoodRecord(List<Good> goodList){
        return goodList.stream().map(GoodMapperCustom::toGoodRecord).toList();
    }
}
