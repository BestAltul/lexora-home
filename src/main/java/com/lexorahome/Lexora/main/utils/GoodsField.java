package com.lexorahome.Lexora.main.utils;

// This class defines the structure for managing products, including:
// white label products, core brands, and add-on brands.
// All defined fields here should be used in all related objects as default values.

public enum GoodsField {

    COLOR("color","Color"),
    TITLE("title","Name"),
    CATEGORY("category","Category of product");


    private final String fieldName;
    private final String description;

    GoodsField(String fieldName,String description){
        this.fieldName = fieldName;
        this.description = description;
    }

    public String getFieldName(){
        return fieldName;
    }

    public String getDescription(){
        return description;
    }
}
