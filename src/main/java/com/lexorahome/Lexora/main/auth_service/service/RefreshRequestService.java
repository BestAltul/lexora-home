package com.lexorahome.Lexora.main.auth_service.service;

import com.lexorahome.Lexora.main.auth_service.entity.PersonRefreshToken;
import com.lexorahome.Lexora.main.auth_service.repository.RefreshRequestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class RefreshRequestService {
    private RefreshRequestRepository refreshRequestRepository;
    private final PasswordEncoder passwordEncoder;

    public Optional<PersonRefreshToken> getPersonRefreshToken(String rawToken,String email){
        List<PersonRefreshToken> refreshTokens = refreshRequestRepository.findAllByPersonEmail(email);
        return  refreshTokens.stream()
                .filter(t->!t.isRevoked() && t.getExpiresAt().isAfter(Instant.now()))
                .filter(t->passwordEncoder.matches(rawToken,t.getTokenHash()))
                .findFirst();
    }
}
