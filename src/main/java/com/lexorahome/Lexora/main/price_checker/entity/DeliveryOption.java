package com.lexorahome.Lexora.main.price_checker.entity;

import com.lexorahome.Lexora.main.entity.RetailPriceListChecker;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Setter
@Getter
@Entity
public class DeliveryOption {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private  String type;
    private  String title;
    private  String arrivalTime;
    private  String bottom;
    private  String quantity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="retail_price_checker_id")
    private RetailPriceListChecker retailPriceListChecker;

    public DeliveryOption(String type, String title, String arrivalTime, String bottom, String quantity) {
        this.type = type;
        this.title = title;
        this.arrivalTime = arrivalTime;
        this.bottom = bottom;
        this.quantity = quantity;
    }
}
