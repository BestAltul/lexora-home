package com.lexorahome.Lexora.main.repository;

import com.lexorahome.Lexora.main.entity.pricechecker.SkuuudleReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;
import java.util.stream.Stream;

@Repository
public interface SkuuudleReportRepository extends JpaRepository<SkuuudleReport, UUID> {

}
