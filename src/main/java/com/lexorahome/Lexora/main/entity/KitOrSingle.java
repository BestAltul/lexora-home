package com.lexorahome.Lexora.main.entity;

import java.util.Objects;

public enum KitOrSingle {
    KIT,
    SINGLE_ITEM,
    NA;
    public static KitOrSingle fromString(String value){
        if(Objects.equals(value, "#N/A")){
            return NA;
        }

        return switch (value.trim().toLowerCase()) {
            case "KIT" -> KIT;
            case "SINGLE_ITEM" -> SINGLE_ITEM;
            default -> NA;
        };
    }
}
