package com.lexorahome.Lexora.main.auth_service.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
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

//    @ManyToMany(fetch = FetchType.EAGER)
//    private Set<Role> roles = new HashSet<>();

}
