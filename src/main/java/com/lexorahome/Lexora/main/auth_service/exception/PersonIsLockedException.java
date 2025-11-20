package com.lexorahome.Lexora.main.auth_service.exception;

public class PersonIsLockedException extends RuntimeException{
    public PersonIsLockedException(String message){
        super(message);
    }
}
