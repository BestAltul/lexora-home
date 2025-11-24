package com.lexorahome.Lexora.main.auth_service.config;

import com.lexorahome.Lexora.main.auth_service.entity.Person;
import com.lexorahome.Lexora.main.auth_service.repository.PersonRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class AdminInitializer {

    String adminEmail = "artur@lexorahome.com";

    private final PersonRepository personRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @PostConstruct
    public void initAdmin(){

        if(personRepository.findByEmail(adminEmail).isEmpty()){
            Person admin = new Person();
            admin.setEmail(adminEmail);
            //admin.setPasswordHash();
            //admin.setRole();
            admin.setLocked(false);
            admin.setFailedAttempts(0);
            //admin.isLocked(false);
            personRepository.save(admin);
        }
    }
}
