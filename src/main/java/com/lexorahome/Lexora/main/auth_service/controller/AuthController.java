package com.lexorahome.Lexora.main.auth_service.controller;

import com.lexorahome.Lexora.main.auth_service.dto.*;
import com.lexorahome.Lexora.main.auth_service.service.JwtService;
import com.lexorahome.Lexora.main.auth_service.service.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v3/auth")
public class AuthController {
    private final PersonService personService;
    private final JwtService jwtService;


    @PostMapping("/sign-up")
    public ResponseEntity<?> signUp(@RequestBody SignUpRecord signUpRecord){
        PersonRecord personRecord = personService.signUp(signUpRecord);
        String accessToken = personService.generateAccessToken(personRecord);
        String refreshToken = personService.generateRefreshToken(personRecord);

        ResponseCookie refreshCookie = ResponseCookie.from("refreshtoken",refreshToken)
                .httpOnly(true)
                .secure(true)
                .path("/refresh")
                .maxAge(1*24*60*60)
                .sameSite("Strict")
                .build();

        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE,refreshCookie.toString()).body(Map.of("accessToken",accessToken));
    }

    @PostMapping("/sign-in")
    public ResponseEntity<?> signIn(@RequestBody SignInRecord signInRecord){
        PersonRecord personRecord = personService.signIn(signInRecord);
        String accessToken = personService.generateAccessToken(personRecord);
        String refreshToken = personService.generateRefreshToken(personRecord);

        ResponseCookie refreshCookie = ResponseCookie.from("refreshToken",refreshToken)
                .httpOnly(true)
                .secure(true)
                .path("/refresh")
                .maxAge(1*24*60*60)
                .sameSite("Strict")
                .build();
        return ResponseEntity.ok(Map.of("accessToken",accessToken,"person", personRecord));
    }

    @PostMapping("/refresh")
    public ResponseEntity<?> refresh(@RequestBody RefreshRequestRecord refreshRequestRecord){

        if(!jwtService.isRefreshTokenValid(refreshRequestRecord)){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,"Invalid Refresh Token");
        }

        PersonRecord personRecord = personService.getPersonRecordByRefreshToken(refreshRequestRecord);
        String newAccessToken = personService.generateAccessToken(personRecord);
        String newRefreshToken = personService.generateRefreshToken(personRecord);

        ResponseCookie refreshCookie = ResponseCookie.from("refreshtoken",newRefreshToken)
                .httpOnly(true)
                .secure(true)
                .path("/refresh")
                .maxAge(1*24*60*60)
                .sameSite("Strict")
                .build();

        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE,refreshCookie.toString()).body(Map.of("accessToken",newAccessToken));
    }
}
