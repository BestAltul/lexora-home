package com.lexorahome.Lexora.main.dto;

import com.lexorahome.Lexora.main.entity.KitOrSingle;
import com.lexorahome.Lexora.main.picture_service.dto.PictureRecord;
import lombok.Builder;

import java.util.List;
import java.util.UUID;

@Builder
public record GoodRecord(UUID id,
                         String sku,
                         String title,
                         boolean isCore,
                         String upc,
                         GoodsCollectionRecord goodsCollectionRecord,
                         GoodRecord coreGood,
                         ColorRecord colorRecord,
                         CategoryRecord categoryRecord,
                         ProductTypeRecord productType,
                         KitOrSingle kitOrSingle,
                         List<PictureRecord> picture) {
}
