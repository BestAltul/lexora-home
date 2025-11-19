package com.lexorahome.Lexora.main.auth_service.controller;

import com.lexorahome.Lexora.main.auth_service.dto.AuthResponse;
import com.lexorahome.Lexora.main.auth_service.dto.PersonRecord;
import com.lexorahome.Lexora.main.auth_service.dto.SignInRecord;
import com.lexorahome.Lexora.main.auth_service.dto.SignUpRecord;
import com.lexorahome.Lexora.main.auth_service.service.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v3/auth")
public class AuthController {
    private final PersonService personService;

    @PostMapping("/sign-up")
    public ResponseEntity<AuthResponse> signUp(@RequestBody SignUpRecord signUpRecord){
        PersonRecord personRecord = personService.signUp(signUpRecord);
        String token = personService.generateToken(personRecord);
        return ResponseEntity.ok(new AuthResponse(personRecord,token));
    }

    @PostMapping("/sign-in")
    public ResponseEntity<?> signIn(@RequestBody SignInRecord signInRecord){
        PersonRecord personRecord = personService.signIn(signInRecord);
        String token = personService.generateToken(personRecord);
        return ResponseEntity.ok(Map.of("Authentication successful",token));
    }
}
