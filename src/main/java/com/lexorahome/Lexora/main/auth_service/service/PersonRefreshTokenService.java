package com.lexorahome.Lexora.main.auth_service.service;

import com.lexorahome.Lexora.main.auth_service.entity.PersonRefreshToken;
import com.lexorahome.Lexora.main.auth_service.repository.PersonRefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class PersonRefreshTokenService {
    private final PersonRefreshTokenRepository personRefreshTokenRepository;

    public void save(PersonRefreshToken personRefreshToken){
        personRefreshTokenRepository.save(personRefreshToken);
    }
}
