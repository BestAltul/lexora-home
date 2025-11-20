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
import java.time.LocalDateTime;
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
            return modelMapper.map(createdPerson,PersonRecord.class);
        }
    }

    public boolean isPersonAuthenticated(SignInRecord signInRecord,Person person){
        if (!person.isLocked() && authenticateService.passwordMatch(signInRecord,person)){
            person.setFailedAttempts(0);
            personRepository.save(person);
            return true;
        }else if(person.isLocked()){

            Instant lockedTime = person.getLockTime();
            Instant currentTime = Instant.now();

            Duration durationSinceLock = Duration.between(lockedTime,currentTime);

            if(durationSinceLock.toHours()<24){
                throw new PersonIsLockedException("Person is still blocked");
            }else{
                person.setLocked(false);
                person.setLockTime(null);
                personRepository.save(person);
                return true;
            }
        }else{
            if(authenticateService.isExceededLimitOfAttempts(person,MAX_ATTEMPTS)){
                person.setLocked(true);
                person.setLockTime(Instant.now());
                personRepository.save(person);
                throw new PersonIsLockedException("Person is blocked for 24 hours");
            }else{
                throw new IncorrectPasswordException("Incorrect password, after 5th attempt you will be blocked for 24 hours");
            }
        }
    }

    public PersonRecord signIn(SignInRecord signInRecord){
        Optional<Person> foundPerson = personRepository.findByEmail(signInRecord.email());
        if(foundPerson.isEmpty()){
            throw new PersonDoesNotExistException("User with "+signInRecord.email()+" doesn't exist");
        }else{
            if (isPersonAuthenticated(signInRecord,foundPerson.get())){
                return modelMapper.map(foundPerson.get(),PersonRecord.class);
            }else{
                throw new IncorrectPasswordException("Incorrect password, after 5th attempt you will be blocked for 24 hours");
            }
        }
    }

    public String generateAccessToken(PersonRecord personRecord){
        return jwtService.generateAccessToken(personRecord);
    }

    public String generateRefreshToken(PersonRecord personRecord){
        return jwtService.generateRefreshToken(personRecord);
    }

    public PersonRecord getPersonRecordByRefreshToken(RefreshRequestRecord refreshRequestRecord){
        Optional<PersonRefreshToken> personRefreshToken = refreshRequestService.getPersonRefreshToken(refreshRequestRecord.refreshToken(),refreshRequestRecord.email());
        return personRefreshToken.map(refreshToken -> modelMapper.map(refreshToken.getPerson(), PersonRecord.class)).orElse(null);
    }
}
