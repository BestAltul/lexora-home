package com.lexorahome.Lexora.main.auth_service.exception;

public class PersonAlreadyExistsException extends RuntimeException{
    public PersonAlreadyExistsException(String message) {
        super(message);
    }
}
