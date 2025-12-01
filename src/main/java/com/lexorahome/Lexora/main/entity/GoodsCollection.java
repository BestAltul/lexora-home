package com.lexorahome.Lexora.main.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.hibernate.envers.Audited;

import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@Audited
@RequiredArgsConstructor
public class GoodsCollection {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="brand_id")
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
