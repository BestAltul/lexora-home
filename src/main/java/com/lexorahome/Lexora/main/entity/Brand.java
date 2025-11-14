package com.lexorahome.Lexora.main.entity;

import lombok.Getter;

@Getter
public enum Brand {
    LEXORA("Lexora"),
    BM("Bell & Modern");

    private final String description;

    Brand(String description){
        this.description = description;
    }
}
