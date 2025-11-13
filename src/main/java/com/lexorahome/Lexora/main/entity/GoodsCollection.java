package com.lexorahome.Lexora.main.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
public class GoodsCollection {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String name;

    @Enumerated(EnumType.STRING)
    private Brand brand;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="retail_id")
    private Retail retail;

    private boolean isCore;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "goods_collection_id")
    private GoodsCollection coreCollection;

    @OneToMany(mappedBy = "goodsCollection")
    private List<Good> good;
}
