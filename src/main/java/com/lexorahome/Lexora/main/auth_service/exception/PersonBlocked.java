package com.lexorahome.Lexora.main.auth_service.exception;

public class PersonBlocked extends RuntimeException{
    public PersonBlocked(String message){
        super(message);
    }
}
