package com.lexorahome.Lexora.main.entity.pricechecker;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Setter
@Getter
public class SkuuudleReport {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String retail;
    private String lexoraSku;
    private String upc;
    private String title;
    private String lexoraCollection;
    private String category;
    private String retailerProductId;
    private BigDecimal promoMap;
    private BigDecimal finalPrice;
    private String linkToTheSource;

}
