package com.lexorahome.Lexora.main.picture_service.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
public class PictureType {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String name;
    private String short_name;

    @OneToMany(mappedBy = "pictureType",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<Picture> pictures;
}
