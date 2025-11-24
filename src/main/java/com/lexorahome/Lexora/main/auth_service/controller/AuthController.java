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

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v3/auth")
public class AuthController {
    private final PersonService personService;
    private final JwtService jwtService;
    private static final String REFRESH_TOKEN_NAME = "refreshToken";


    @PostMapping("/sign-up")
    public ResponseEntity<?> signUp(@RequestBody SignUpRecord signUpRecord){
        PersonRecord personRecord = personService.signUp(signUpRecord);
        String accessToken = personService.generateAccessToken(personRecord);
        String refreshToken = personService.generateRefreshToken(personRecord);

        ResponseCookie refreshCookie = buildRefreshCookie(refreshToken);
        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE,refreshCookie.toString()).body(Map.of("accessToken",accessToken));
    }

    @PostMapping("/sign-in")
    public ResponseEntity<?> signIn(@RequestBody SignInRecord signInRecord){
        SignInResult signInResult = personService.signIn(signInRecord);
        String accessToken = personService.generateAccessToken(signInResult.personRecord());
        String refreshToken = signInResult.refreshToken();

        ResponseCookie refreshCookie = buildRefreshCookie(refreshToken);

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE,refreshCookie.toString())
                .body(Map.of("accessToken",accessToken,"person",signInResult.personRecord()));
    }

    @PostMapping("/refresh")
    public ResponseEntity<?> refresh(@CookieValue (name = REFRESH_TOKEN_NAME, required=false) String refreshToken){

        if(refreshToken==null || !jwtService.isRefreshTokenValid(refreshToken)){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,"Invalid Refresh Token");
        }

        PersonRecord personRecord = personService.getPersonRecordByRefreshToken(refreshToken);
        String newAccessToken = personService.generateAccessToken(personRecord);
        String newRefreshToken = personService.generateRefreshToken(personRecord);

        ResponseCookie refreshCookie = buildRefreshCookie(newRefreshToken);

        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE,refreshCookie.toString()).body(Map.of("accessToken",newAccessToken));
    }

    private ResponseCookie buildRefreshCookie(String token){
        return ResponseCookie.from(REFRESH_TOKEN_NAME, token)
                .httpOnly(true)
                .secure(true)
                .path("/api/v3/auth")
                .maxAge(1 * 24 * 60 * 60)
                .sameSite("Strict")
                .build();
    }
}
