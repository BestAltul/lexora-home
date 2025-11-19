package com.lexorahome.Lexora.main.auth_service.exception;

public class PersonDoesNotExist extends RuntimeException{
    public PersonDoesNotExist(String message) {
        super(message);
    }
}

