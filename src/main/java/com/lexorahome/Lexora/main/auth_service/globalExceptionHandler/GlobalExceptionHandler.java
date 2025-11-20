package com.lexorahome.Lexora.main.auth_service.globalExceptionHandler;

import com.lexorahome.Lexora.main.auth_service.exception.IncorrectPasswordException;
import com.lexorahome.Lexora.main.auth_service.exception.PersonAlreadyExistsException;
import com.lexorahome.Lexora.main.auth_service.exception.PersonDoesNotExistException;
import com.lexorahome.Lexora.main.auth_service.exception.PersonIsLockedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(PersonIsLockedException.class)
    public ResponseEntity<Map<String,Object>> handleUserLocked(PersonIsLockedException ex){
        Map<String,Object> response = new HashMap<>();
        response.put("error","User account is locked");
        response.put("message",ex.getMessage());
        response.put("timestamp",System.currentTimeMillis());
        return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(IncorrectPasswordException.class)
    public ResponseEntity<Map<String,Object>> handleIncorrectPassword(IncorrectPasswordException ex){
        Map<String,Object> response = new HashMap<>();
        response.put("error","Incorrect password");
        response.put("message",ex.getMessage());
        response.put("timestamp",System.currentTimeMillis());
        return new ResponseEntity<>(response,HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(PersonDoesNotExistException.class)
    public ResponseEntity<Map<String,Object>> handlePersonDoesNotExist(PersonDoesNotExistException ex){
        Map<String,Object> response = new HashMap<>();
        response.put("error","Person doesn't exist");
        response.put("message",ex.getMessage());
        response.put("timestamp", Instant.now());
        return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(PersonAlreadyExistsException.class)
    public ResponseEntity<Map<String,Object>> handlePersonAlreadyExistsException(PersonDoesNotExistException ex){
        Map<String,Object> response = new HashMap<>();
        response.put("error","Person is already exists");
        response.put("message",ex.getMessage());
        response.put("timestamp",Instant.now());
        return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
    }
}
