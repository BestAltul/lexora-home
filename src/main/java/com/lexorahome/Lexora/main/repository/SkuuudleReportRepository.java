package com.lexorahome.Lexora.main.repository;

import com.lexorahome.Lexora.main.entity.pricechecker.SkuuudleReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;
import java.util.stream.Stream;

public interface SkuuudleReportRepository extends JpaRepository<SkuuudleReport, UUID> {

    

}
