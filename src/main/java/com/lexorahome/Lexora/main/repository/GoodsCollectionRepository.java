package com.lexorahome.Lexora.main.repository;

import com.lexorahome.Lexora.main.entity.GoodsCollection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface GoodsCollectionRepository extends JpaRepository<GoodsCollection, UUID> {
    Optional<GoodsCollection> findByNameIgnoreCase(String name);
}
