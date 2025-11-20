package com.lexorahome.Lexora.main.auth_service.exception;

public class PersonDoesNotExistException extends RuntimeException{
    public PersonDoesNotExistException(String message) {
        super(message);
    }
}

