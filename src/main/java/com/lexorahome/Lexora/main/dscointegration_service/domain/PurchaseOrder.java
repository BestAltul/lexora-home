package com.lexorahome.Lexora.main.dscointegration_service.domain;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
public class PurchaseOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String poNumber;
    private String retailerCreateDay;
    private String dscoNumber;

    private String dscoStatus;
    private String dscoOrderId;


    private  String dscoLifecycle;


    private String dscoLastUpdate;
    private String acknowledgeByDate;

    private String currencyCode;


    @OneToMany
    private List<LineItem> lineItemList;






}
