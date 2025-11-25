package com.lexorahome.Lexora.main.auth_service.service;

import com.lexorahome.Lexora.main.auth_service.dto.*;
import com.lexorahome.Lexora.main.auth_service.entity.Person;
import com.lexorahome.Lexora.main.auth_service.entity.PersonRefreshToken;
import com.lexorahome.Lexora.main.auth_service.exception.IncorrectPasswordException;
import com.lexorahome.Lexora.main.auth_service.exception.PersonAlreadyExistsException;

import com.lexorahome.Lexora.main.auth_service.exception.PersonDoesNotExistException;
import com.lexorahome.Lexora.main.auth_service.exception.PersonIsLockedException;
import com.lexorahome.Lexora.main.auth_service.repository.PersonRepository;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Value;

@Service
@RequiredArgsConstructor
public class PersonService {
    private final PersonRepository personRepository;
    private final ModelMapper modelMapper;
    private final AuthenticateService authenticateService;
    private final JwtService jwtService;
    private final int MAX_ATTEMPTS = 5;
    private final RefreshRequestService refreshRequestService;
    private final PersonRefreshTokenService personRefreshTokenService;
    private final TokenHashService tokenHashService;
    private final EmailService emailService;
    @Value("${app.reset-password-url}")
    private String resetPasswordUrl;
    @Value("${app.jwt-secret}")
    private String secretKey;
    private final PasswordEncoder passwordEncoder;

    private boolean isPersonExisting(String email){
        return personRepository.findByEmail(email).isPresent();
    }

    private Person createNewPerson(SignUpRecord signUpRecord){
        return modelMapper.map(signUpRecord,Person.class);
    }

    public PersonRecord signUp(SignUpRecord signUpRecord){

        if(isPersonExisting(signUpRecord.email())){
            throw new PersonAlreadyExistsException("Email "+signUpRecord.email()+" has been already used");
        } else{
            Person createdPerson = createNewPerson(signUpRecord);
            createdPerson.setPassword(authenticateService.getHashOfPassword(signUpRecord.password()));
            createdPerson.setCreatedAt(Instant.now());
            createdPerson.setEmailVerified(true);
            personRepository.save(createdPerson);

            PersonRefreshToken refreshToken = PersonRefreshToken.builder()
                    .person(createdPerson)
                    .tokenHash(jwtService.generateRefreshToken())
                    .createdAt(Instant.now())
                    .expiresAt(Instant.now().plus(1, ChronoUnit.DAYS))
                    .revoked(false)
                    .deviceInfo("")
                    .build();

            personRefreshTokenService.save(refreshToken);

            return modelMapper.map(createdPerson,PersonRecord.class);
        }
    }

    public boolean isPersonAuthenticated(SignInRecord signInRecord,Person person){
        boolean passwordMatches;

        boolean inputEmpty = signInRecord.password().isBlank();
        boolean storedEmpty = person.getPassword() == null;

//        if(inputEmpty && storedEmpty){
//            return true;
//        }

        if(inputEmpty){
            throw new IncorrectPasswordException("The password can't be empty");
        }

        if(storedEmpty){
            return false;
        }

        passwordMatches = authenticateService.passwordMatch(signInRecord.password(), person.getPassword());

        if (!person.isLocked()) {
            if (passwordMatches) {
                person.setFailedAttempts(0);
                personRepository.save(person);
                return true;
            }

            if (authenticateService.isExceededLimitOfAttempts(person, MAX_ATTEMPTS)) {
                person.setLocked(true);
                person.setLockTime(Instant.now());
                personRepository.save(person);
                throw new PersonIsLockedException("Person is locked for 24 hours");
            }

            person.setFailedAttempts(person.getFailedAttempts()+1);
            personRepository.save(person);

            throw new IncorrectPasswordException("Incorrect password, after "+(5-person.getFailedAttempts())+"-th attempt you will be locked for 24 hours");
        }

        Duration sinceLock = Duration.between(person.getLockTime(), Instant.now());

        int hours = (int) sinceLock.toHours();
        int minutes = (int) sinceLock.toMinutes();

        if (sinceLock.toHours() < 24) {
            int remainingHours = 23-hours;
            int remailMinutes = 59-minutes;
            throw new PersonIsLockedException("Person is still locked. Remaining time: "+remainingHours +" hours "+remailMinutes + " minutes");
        }

        if (passwordMatches) {
            person.setLocked(false);
            person.setLockTime(null);
            person.setFailedAttempts(0);
            personRepository.save(person);
            return true;
        }

        throw new IncorrectPasswordException("Incorrect password, after 5th attempt you will be locked for 24 hours");
    }



    public String issueNewRefreshToken(Person person){

        personRefreshTokenService.revokeToken(person);

        String refreshToken = jwtService.generateRefreshToken();
        String tokenHash = tokenHashService.hashToken(refreshToken);

        PersonRefreshToken entity = PersonRefreshToken.builder()
                .person(person)
                .tokenHash(tokenHash)
                .createdAt(Instant.now())
                .expiresAt(Instant.now().plus(1,ChronoUnit.DAYS))
                .revoked(false)
                .build();

        personRefreshTokenService.save(entity);

        return refreshToken;
    }

    public SignInResult signIn(SignInRecord signInRecord){
        Optional<Person> foundPerson = personRepository.findByEmail(signInRecord.email());
        if(foundPerson.isEmpty()){
            throw new PersonDoesNotExistException("User with "+signInRecord.email()+" doesn't exist");
        }
        Person person = foundPerson.get();
        if (!isPersonAuthenticated(signInRecord,person)){
           throw new IncorrectPasswordException("Incorrect password, after 5th attempt you will be locked for 24 hours");
        }

        String refreshToken = issueNewRefreshToken(person);

        //  return new SignInResult(modelMapper.map(foundPerson.get(),PersonRecord.class),refreshToken);
        return new SignInResult(PersonRecord.builder()
                .email(person.getEmail())
                .build(), refreshToken);
    }

    public String generateAccessToken(PersonRecord personRecord){
        return jwtService.generateAccessToken(personRecord);
    }

    public String generateRefreshToken(PersonRecord personRecord){
        return jwtService.generateRefreshToken();
    }

    public PersonRecord getPersonRecordByRefreshToken(String refreshToken){
        Optional<PersonRefreshToken> personRefreshToken = refreshRequestService.getPersonRefreshToken(refreshToken);
        return personRefreshToken.map(rToken -> modelMapper.map(rToken.getPerson(), PersonRecord.class)).orElse(null);
    }

    public void resetPassword(String email) {
        Optional<Person> userOpt = personRepository.findByEmail(email);
        if (userOpt.isEmpty()) return;

        Key key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
        String token = jwtService.generateResetToken(email, key);
        String link = resetPasswordUrl + "?token=" + token;

        emailService.sendResetPasswordEmail(email, link);
    }

    public boolean changePassword(String token, String newPassword) {
        Key key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));

        try {
            String email = jwtService.validateResetToken(token, key);

            Optional<Person> personOpt = personRepository.findByEmail(email);

            if (personOpt.isEmpty()) {
                throw new RuntimeException("User not found");
            }

            Person person = personOpt.get();

            String hashedPassword = passwordEncoder.encode(newPassword);
            person.setPassword(hashedPassword);

            personRepository.save(person);

            return true;

        } catch (Exception e) {
            throw new RuntimeException("Reset token is not valid "+e.getMessage());
        }
    }


}
