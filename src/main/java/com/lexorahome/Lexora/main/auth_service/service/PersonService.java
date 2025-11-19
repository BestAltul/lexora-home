package com.lexorahome.Lexora.main.auth_service.service;

import com.lexorahome.Lexora.main.auth_service.dto.PersonRecord;
import com.lexorahome.Lexora.main.auth_service.dto.SignInRecord;
import com.lexorahome.Lexora.main.auth_service.dto.SignUpRecord;
import com.lexorahome.Lexora.main.auth_service.entity.Person;
import com.lexorahome.Lexora.main.auth_service.exception.IncorrectPassword;
import com.lexorahome.Lexora.main.auth_service.exception.PersonAlreadyExistsException;
import com.lexorahome.Lexora.main.auth_service.exception.PersonBlocked;
import com.lexorahome.Lexora.main.auth_service.exception.PersonDoesNotExist;
import com.lexorahome.Lexora.main.auth_service.repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

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
        if (!person.isBlocked() && authenticateService.passwordMatch(signInRecord,person)){
            person.setFailedAttempts(0);
            personRepository.save(person);
            return true;
        }else if(person.isBlocked()){
            if(person.getLockTime()-Instant.now()<24){

            }
        }else{
            if(authenticateService.isExceededLimitOfAttempts(person,MAX_ATTEMPTS)){
                person.setBlocked(true);
                person.setLockTime(Instant.now());
                personRepository.save(person);
                throw new PersonBlocked("Person blocked for 24 hours");
            }else{
                throw new IncorrectPassword("Incorrect password, after 5th attempt you will be blocked for 24 hours");
            }
        }
    }

    public PersonRecord signIn(SignInRecord signInRecord){
        Optional<Person> foundPerson = personRepository.findByEmail(signInRecord.email());
        if(foundPerson.isEmpty()){
            throw new PersonDoesNotExist("User with "+signInRecord.email()+" doesn't exist");
        }else{
            if (isPersonAuthenticated(signInRecord,foundPerson.get())){
                return modelMapper.map(foundPerson.get(),PersonRecord.class);
            }else{
                throw new IncorrectPassword("Incorrect password, after 5th attempt you will be blocked for 24 hours");
            }
        }
    }

    public String generateToken(PersonRecord personRecord){
        return jwtService.generateToken(personRecord);
    }
}
