package com.lexorahome.Lexora.main.repository;

import com.lexorahome.Lexora.main.entity.Good;
import com.lexorahome.Lexora.main.entity.PriceList;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface PriceListRepository extends JpaRepository<PriceList, UUID> {

    @Query("""
SELECT pl
FROM PriceList pl
WHERE pl.promoMap IS NOT NULL
AND NOT EXISTS (
    SELECT 1
    FROM RetailPriceListChecker rplc
    WHERE rplc.priceList = pl
)
ORDER BY pl.promoMap DESC
""")
    List<PriceList> findGoodsWithoutCheckerOrderPromoDesc(Pageable pageable);

}
