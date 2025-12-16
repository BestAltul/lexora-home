package com.lexorahome.Lexora.main.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
public class RetailPriceListChecker {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @OneToOne
    private PriceList priceList;

    @OneToOne
    private Retail retail;
    private Instant checkedDate;
    private Boolean notFound;

    private BigDecimal companyPrice;

    private BigDecimal price;
    private BigDecimal price_was;
    private BigDecimal price_saving;
    private BigDecimal percentage_off;

}
