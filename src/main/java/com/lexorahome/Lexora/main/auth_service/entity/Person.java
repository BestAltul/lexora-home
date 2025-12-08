package com.lexorahome.Lexora.main.auth_service.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.Instant;
import java.util.*;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Person implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String firstName;
    private String lastName;
    @Column(unique = true,nullable = false)
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
    @JoinTable(name="user_roles",joinColumns = @JoinColumn(name="person_id"),inverseJoinColumns = @JoinColumn(name="role_id"))
    private Set<Role> roles;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    @Override
    public String getUsername() {
        return email;
    }
}
