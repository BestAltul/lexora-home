package com.lexorahome.Lexora.main.auth_service.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.apache.poi.ss.formula.IStabilityClassifier;

import java.time.Instant;
import java.util.UUID;

@Entity
@AllArgsConstructor
@Getter
@Setter
@Builder
public class PersonRefreshToken {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "person_id",insertable = false,updatable = false)
    private Person person;
    private String tokenHash;
    private Instant createdAt;
    private Instant expiresAt;
    private boolean revoked;
    private String deviceInfo;
}
