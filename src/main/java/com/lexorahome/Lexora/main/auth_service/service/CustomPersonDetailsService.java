package com.lexorahome.Lexora.main.auth_service.service;

import com.lexorahome.Lexora.main.auth_service.entity.Person;
import com.lexorahome.Lexora.main.auth_service.repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CustomPersonDetailsService implements UserDetailsService {
    private final PersonRepository personRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return personRepository.findByEmail(username).orElseThrow(()->new UsernameNotFoundException("User not found"));
    }
}
