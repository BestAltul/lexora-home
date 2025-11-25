package com.lexorahome.Lexora.main.auth_service.service;

import com.lexorahome.Lexora.main.auth_service.dto.PersonRecord;
import com.lexorahome.Lexora.main.auth_service.dto.SignInRecord;
import com.lexorahome.Lexora.main.auth_service.entity.PersonRefreshToken;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import io.jsonwebtoken.Jwts;

import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.Date;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class JwtService {
    private final ModelMapper modelMapper;
    private final RefreshRequestService refreshRequestService;
    private final SecureRandom secureRandom;

    @Value("${app.jwt-private-key}")
    private String privateKeyStr;

    @Value("${app.jwt-public-key}")
    private String publicKeyStr;

    private PrivateKey privateKey;
    private PublicKey publicKey;


    public String generateAccessToken(PersonRecord personRecord){
        return Jwts.builder()
                .setSubject(personRecord.email())
                .claim("role","USER")
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis()+600000))
                .signWith(privateKey, SignatureAlgorithm.RS256)
                .compact();
    }

    public String generateRefreshToken(){
        byte[] bytes = new byte[32];
        secureRandom.nextBytes(bytes);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);
    }

    private PrivateKey loadPrivateKey(String key) throws Exception {
        String cleaned = key
                .replaceAll("\\n", "")
                .replace("-----BEGIN PRIVATE KEY-----", "")
                .replace("-----END PRIVATE KEY-----", "");
        byte[] decoded = Base64.getDecoder().decode(cleaned);
        return KeyFactory.getInstance("RSA").generatePrivate(new PKCS8EncodedKeySpec(decoded));
    }

    private PublicKey loadPublicKey(String key) throws Exception {
        String cleaned = key
                .replaceAll("\\n", "")
                .replace("-----BEGIN PUBLIC KEY-----", "")
                .replace("-----END PUBLIC KEY-----", "");
        byte[] decoded = Base64.getDecoder().decode(cleaned);
        return KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(decoded));
    }

    public boolean isRefreshTokenValid(String refreshToken){

        Optional<PersonRefreshToken> personRefreshToken = refreshRequestService.getPersonRefreshToken(refreshToken);

        return personRefreshToken.isPresent();
    }

    public String generateResetToken(String email, Key secret_key){
        long expirationMillis = 3600_000; // 1 час
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + expirationMillis);

        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(secret_key, SignatureAlgorithm.HS256)
                .compact();
    }

//    @PostConstruct
//    private void initKeys() throws Exception {
//        this.privateKey = loadPrivateKey(privateKeyStr);
//        this.publicKey = loadPublicKey(publicKeyStr);
//    }

    public String validateResetToken(String token, Key secret_key) throws Exception  {
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(secret_key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

            return claims.getSubject();
        } catch (ExpiredJwtException e) {
            throw new Exception("Token expired");
        } catch (Exception e) {
            throw new Exception("Invalid token");
        }
    }
}
