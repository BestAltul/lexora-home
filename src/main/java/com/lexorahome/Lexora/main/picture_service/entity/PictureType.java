package com.lexorahome.Lexora.main.picture_service.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.envers.Audited;

import java.util.List;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Audited
@Entity
public class PictureType {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String name;

    @Column(name = "short_name")
    private String shortName;

    @OneToMany(mappedBy = "pictureType",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<Picture> pictures;
}
