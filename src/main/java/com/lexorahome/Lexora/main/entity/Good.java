package com.lexorahome.Lexora.main.entity;

import com.lexorahome.Lexora.main.picture_service.entity.Picture;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.envers.Audited;

import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@Builder
@Audited
@AllArgsConstructor
@NoArgsConstructor
public class Good {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String sku;
    private String oldSku;
    private String title;
    private boolean isCore;
    private String upc;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="goods_collection_id")
    private GoodsCollection goodsCollection;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "core_good_id")
    private Good coreGood;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="color_id")
    private Color color;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="product_type_id")
    private ProductType productType;

    private KitOrSingle kitOrSingle;

    @OneToMany(mappedBy = "good", cascade = CascadeType.ALL)
    private List<Picture> picture;
}
