package com.lexorahome.Lexora.main.auth_service.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;

import java.util.UUID;

@RequiredArgsConstructor
@Getter
@Setter
@Entity
public class Role implements GrantedAuthority {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(unique = true,nullable = false)
    private final String name;


    @Override
    public String getAuthority() {
        return name;
    }
}
