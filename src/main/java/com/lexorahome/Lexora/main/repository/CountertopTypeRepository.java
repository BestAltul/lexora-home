package com.lexorahome.Lexora.main.repository;

import com.lexorahome.Lexora.main.entity.CountertopType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CountertopTypeRepository extends JpaRepository<CountertopType, UUID> {
    Optional<CountertopType> findByNameIgnoringCase(String name);
}
