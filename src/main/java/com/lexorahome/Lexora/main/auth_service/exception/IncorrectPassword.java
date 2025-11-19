package com.lexorahome.Lexora.main.auth_service.exception;

public class IncorrectPassword extends RuntimeException{
    public IncorrectPassword(String message){
        super(message);
    }
}
