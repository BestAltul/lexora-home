package com.lexorahome.Lexora.main.auth_service.service;

import com.lexorahome.Lexora.main.auth_service.entity.Person;
import com.lexorahome.Lexora.main.auth_service.exception.PersonDoesNotExistException;
import com.lexorahome.Lexora.main.auth_service.repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@RequiredArgsConstructor
@Service
public class PasswordResetService {

    private final PersonRepository personRepository;
    private final EmailService emailService;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public void sendResetLink(String email){

        Person person = personRepository.findByEmail(email).orElseThrow(()->new PersonDoesNotExistException("User not found"));

        String token = UUID.randomUUID().toString();

     //   person.setReset

        String resetLink = "http://localhost:5173/reset-password?token="+ token;

        emailService.sendResetPasswordEmail(email,resetLink);

    }

}
