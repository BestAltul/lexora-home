package com.lexorahome.Lexora.main.repository;

import com.lexorahome.Lexora.main.entity.Retail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface RetailRepository extends JpaRepository<Retail, UUID> {
    Optional<Retail> findByNameIgnoringCase(String name);
}
