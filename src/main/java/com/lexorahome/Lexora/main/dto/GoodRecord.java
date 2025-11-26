package com.lexorahome.Lexora.main.dto;

import com.lexorahome.Lexora.main.entity.KitOrSingle;
import lombok.Builder;

import java.util.UUID;

@Builder
public record GoodRecord(UUID id,
                         String title,
                         boolean isScore,
                         String upc,
                         GoodsCollectionRecord goodsCollectionRecord,
                         GoodRecord coreGood,
                         ColorRecord colorRecord,
                         CategoryRecord categoryRecord,
                         ProductTypeRecord productTypeRecord,
                         KitOrSingle kitOrSingle) {
}
