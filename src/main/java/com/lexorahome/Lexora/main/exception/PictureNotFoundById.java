package com.lexorahome.Lexora.main.exception;

public class PictureNotFoundById extends IllegalArgumentException{
    public PictureNotFoundById(String message){
        super(message);
    }
}
