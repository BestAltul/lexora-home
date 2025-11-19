package com.lexorahome.Lexora.main.auth_service.service;

import com.lexorahome.Lexora.main.auth_service.dto.PersonRecord;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Service;
import io.jsonwebtoken.Jwts;

import java.security.PrivateKey;
import java.util.Date;

@Service
public class JwtService {

    private PrivateKey privateKey;

    public String generateToken(PersonRecord personRecord){
        return Jwts.builder()
                .setSubject(personRecord.email())
                .claim("role","USER")
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis()+3600))
                .signWith(privateKey, SignatureAlgorithm.RS256)
                .compact();
    }
}
