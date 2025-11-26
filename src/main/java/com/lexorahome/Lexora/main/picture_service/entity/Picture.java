package com.lexorahome.Lexora.main.picture_service.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.hibernate.envers.Audited;

import java.time.Instant;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Audited
@Entity
public class Picture {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String name;
    private String link;
    private int priority;
    private String notes;
    private Instant createdAt;
    private Instant modifiedAt;
    @Enumerated(EnumType.STRING)
    private PictureStatus pictureStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="picture_type_id")
    private PictureType pictureType;

}
