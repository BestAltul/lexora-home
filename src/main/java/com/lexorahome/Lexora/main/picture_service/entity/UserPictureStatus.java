package com.lexorahome.Lexora.main.picture_service.entity;


import com.lexorahome.Lexora.main.auth_service.entity.Person;
import jakarta.persistence.*;

import java.time.Instant;
import java.util.UUID;

public class UserPictureStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String name;
    private Instant createdAt;
    private Instant modifiedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="person_id")
    private Person person;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="picture_id")
    private Picture picture;

    private String notes;
    private boolean correct;



}
