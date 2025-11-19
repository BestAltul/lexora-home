package com.lexorahome.Lexora.main.auth_service.service;

import com.lexorahome.Lexora.main.auth_service.dto.SignInRecord;
import com.lexorahome.Lexora.main.auth_service.entity.Person;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticateService {
    private final PasswordEncoder passwordEncoder;

    public String getHashOfPassword(String password){
        return passwordEncoder.encode(password);
    }

    public boolean passwordMatch(SignInRecord signInRecord,Person person){
        return passwordEncoder.matches(signInRecord.password(), person.getPassword());
    }

    public boolean isExceededLimitOfAttempts(Person person,int limitAttempts){
        return person.getFailedAttempts() + 1 > limitAttempts;
    }
}
