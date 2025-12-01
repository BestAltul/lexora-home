package com.lexorahome.Lexora.main.picture_service.repository;

import com.lexorahome.Lexora.main.picture_service.entity.Picture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface PictureRepository extends JpaRepository<Picture, UUID> {
    List<Picture> findAllByGoodId(UUID id);
}
