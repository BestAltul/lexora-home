package com.lexorahome.Lexora.main.dscointegration_service.domain;

import java.math.BigDecimal;

public class LineItem {

    private Long id;
    private PurchaseOrder purchaseOrder;
    // do wee need that?
    // private Good good;
    private int lineNumber;
    private String title;
    private String sku;
    private String upc;
    private String ean;
    private String mpn;
    private String dscoItemId;

    private int quantity;
    private BigDecimal consumerPrice;
    private BigDecimal retailPrice;
    private String status;

    private String dscoSupplierId;
    private String dscoTradingPartnerId;

    private String packingInstruction;
    private String shipInstruction;







}
