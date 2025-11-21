package com.lexorahome.Lexora.main.auth_service.service;

import com.lexorahome.Lexora.main.auth_service.dto.PersonRecord;
import com.lexorahome.Lexora.main.auth_service.dto.RefreshRequestRecord;
import com.lexorahome.Lexora.main.auth_service.entity.PersonRefreshToken;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import io.jsonwebtoken.Jwts;

import java.security.PrivateKey;
import java.util.Date;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@RequiredArgsConstructor
@Service
public class JwtService {
    private final ModelMapper modelMapper;
    private final RefreshRequestService refreshRequestService;
    private PrivateKey privateKey;

    public String generateAccessToken(PersonRecord personRecord){
        return Jwts.builder()
                .setSubject(personRecord.email())
                .claim("role","USER")
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis()+600000))
                .signWith(privateKey, SignatureAlgorithm.RS256)
                .compact();
    }

    public String generateRefreshToken(String email){
        return Jwts.builder()
                .setSubject(email)
                .claim("role","USER")
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + TimeUnit.HOURS.toMillis(24)))
                .signWith(privateKey, SignatureAlgorithm.RS256)
                .compact();
    }

    public boolean isRefreshTokenValid(RefreshRequestRecord refreshRequestRecord){

        Optional<PersonRefreshToken> personRefreshToken = refreshRequestService.getPersonRefreshToken(refreshRequestRecord.refreshToken(),refreshRequestRecord.email());

        return personRefreshToken.isPresent();
    }
}
