package com.lexorahome.Lexora.main.auth_service.service;

import com.lexorahome.Lexora.main.auth_service.dto.PersonRecord;
import com.lexorahome.Lexora.main.auth_service.dto.RefreshRequestRecord;
import com.lexorahome.Lexora.main.auth_service.dto.SignInRecord;
import com.lexorahome.Lexora.main.auth_service.dto.SignUpRecord;
import com.lexorahome.Lexora.main.auth_service.entity.Person;
import com.lexorahome.Lexora.main.auth_service.entity.PersonRefreshToken;
import com.lexorahome.Lexora.main.auth_service.exception.IncorrectPasswordException;
import com.lexorahome.Lexora.main.auth_service.exception.PersonAlreadyExistsException;

import com.lexorahome.Lexora.main.auth_service.exception.PersonDoesNotExistException;
import com.lexorahome.Lexora.main.auth_service.exception.PersonIsLockedException;
import com.lexorahome.Lexora.main.auth_service.repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Optional;


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

        boolean passwordMatches = authenticateService.passwordMatch(signInRecord, person);

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

            throw new IncorrectPasswordException("Incorrect password, after 5th attempt you will be locked for 24 hours");
        }

        Duration sinceLock = Duration.between(person.getLockTime(), Instant.now());

        if (sinceLock.toHours() < 24) {
            throw new PersonIsLockedException("Person is still locked");
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

    public PersonRecord signIn(SignInRecord signInRecord){
        Optional<Person> foundPerson = personRepository.findByEmail(signInRecord.email());
        if(foundPerson.isEmpty()){
            throw new PersonDoesNotExistException("User with "+signInRecord.email()+" doesn't exist");
        }
        Person person = foundPerson.get();
        if (!isPersonAuthenticated(signInRecord,person)){
           throw new IncorrectPasswordException("Incorrect password, after 5th attempt you will be locked for 24 hours");
        }

        String refreshToken = jwtService.generateRefreshToken();
        String tokenHash = tokenHashService.hashToken(refreshToken);

        PersonRefreshToken refreshTokenEntity = PersonRefreshToken.builder()
                .person(person)
                .tokenHash(tokenHash)
                .createdAt(Instant.now())
                .expiresAt(Instant.now().plus(1, ChronoUnit.DAYS))
                .revoked(false)
                .deviceInfo("")
                .build();
        personRefreshTokenService.save(refreshTokenEntity);

        return modelMapper.map(foundPerson.get(),PersonRecord.class);

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

}
