package com.lexorahome.Lexora.main.auth_service.service;

import com.lexorahome.Lexora.main.auth_service.entity.Person;
import com.lexorahome.Lexora.main.auth_service.entity.PersonRefreshToken;
import com.lexorahome.Lexora.main.auth_service.repository.PersonRefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.fasterxml.jackson.databind.jsonFormatVisitors.JsonValueFormat.UUID;

@RequiredArgsConstructor
@Service
public class PersonRefreshTokenService {
    private final PersonRefreshTokenRepository personRefreshTokenRepository;

    public void save(PersonRefreshToken personRefreshToken){
        personRefreshTokenRepository.save(personRefreshToken);
    }

    public void revokeToken(Person person) {
        Optional<PersonRefreshToken> refreshToken =
                personRefreshTokenRepository.findByPersonIdAndRevokedFalse(person.getId());

        refreshToken.ifPresent(t -> {
            t.setRevoked(true);
            personRefreshTokenRepository.save(t);
        });
    }

}
