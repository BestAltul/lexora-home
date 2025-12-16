package com.lexorahome.Lexora.main.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
public class PriceList {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "retail_id",nullable = false)
    private Retail retail;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name="pricelist_goods",joinColumns = @JoinColumn(name="pricelist_id"),inverseJoinColumns = @JoinColumn(name="goods_id"))
    private Set<Good> good = new HashSet<>();

    //private String sku;
  //  private String exclusiveSku;
    private String retailItemId;

    private LocalDate startAt;
    private LocalDate endAt;

    private BigDecimal promoMap;
    private BigDecimal promoWh;

}
