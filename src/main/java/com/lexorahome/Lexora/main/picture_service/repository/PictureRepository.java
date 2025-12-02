package com.lexorahome.Lexora.main.picture_service.repository;

import com.lexorahome.Lexora.main.picture_service.entity.Picture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface PictureRepository extends JpaRepository<Picture, UUID> {
    List<Picture> findAllByGoodId(UUID id);

    @Query("SELECT p FROM Picture p " +
            "LEFT JOIN FETCH p.pictureType " +
            "LEFT JOIN FETCH p.good " +
            "WHERE p.id = :id")
    Optional<Picture> findByIdWithRelations(@Param("id") UUID id);
}
