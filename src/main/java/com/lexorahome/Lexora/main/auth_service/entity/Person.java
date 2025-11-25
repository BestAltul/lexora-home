package com.lexorahome.Lexora.main.auth_service.entity;

import com.lexorahome.Lexora.main.entity.Role;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.cglib.core.Local;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String firstName;
    private String lastName;
    private String email;
    private String password;

    private Instant createdAt;
    private Instant lastLogin;
    private Instant lockTime;
    private Instant tokenExpiry;
    private boolean isEmailVerified;
    private int failedAttempts;
    private boolean isLocked;

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Role> roles = new HashSet<>();

}
