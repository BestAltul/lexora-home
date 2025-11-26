package com.lexorahome.Lexora.main.repository;

import com.lexorahome.Lexora.main.entity.Good;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface GoodRepository extends JpaRepository<Good, UUID> {
    Optional<Good> findBySku(String sku);
    @EntityGraph(attributePaths = {"category","goodsCollection","productType"})
    List<Good> findAll();
}
