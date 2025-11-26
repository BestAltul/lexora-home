package com.lexorahome.Lexora.main.dto;

import lombok.Builder;

import java.util.UUID;

@Builder
public record GoodsCollectionRecord(UUID id,
                                    String name,
                                    BrandRecord brandRecord,
                                    RetailRecord retailRecord,
                                    boolean isScore,
                                    GoodsCollectionRecord coreCollectionRecord
                                    ) {
}
