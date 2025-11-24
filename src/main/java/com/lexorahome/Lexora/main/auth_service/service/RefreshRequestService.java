package com.lexorahome.Lexora.main.auth_service.service;

import com.lexorahome.Lexora.main.auth_service.entity.PersonRefreshToken;
import com.lexorahome.Lexora.main.auth_service.repository.PersonRefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class RefreshRequestService {
    private PersonRefreshTokenRepository personRefreshTokenRepository;
    private final PasswordEncoder passwordEncoder;

    public Optional<PersonRefreshToken> getPersonRefreshToken(String rawToken){
     //   List<PersonRefreshToken> refreshTokens = personRefreshTokenRepository.findAllByPersonEmail(email);

        String hash = passwordEncoder.encode(rawToken);

        return  personRefreshTokenRepository.findByTokenHash(hash)
                .filter(t -> !t.isRevoked())
                .filter(t -> t.getExpiresAt().isAfter(Instant.now()));
    }
}
