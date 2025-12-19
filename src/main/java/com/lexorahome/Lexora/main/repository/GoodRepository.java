package com.lexorahome.Lexora.main.repository;

import com.lexorahome.Lexora.main.entity.Good;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface GoodRepository extends JpaRepository<Good, UUID> {
    Optional<Good> findBySku(String sku);
    @EntityGraph(attributePaths = {"category","goodsCollection","productType"})
    List<Good> findAll();
    Optional<Good> findByRetailItemId(String id);

    @Query("""
        select g
        from SkuuudleReport s, Good g
        where g.sku = s.lexoraSku
          and s.retail = :retail
          and not exists (
              select pl
              from PriceList pl
              join pl.good pg
              where pg = g
          )
    """)
    List<Good> findGoodsNotInAnyPricelistByRetail(
            @Param("retail") String retail
    );

}
