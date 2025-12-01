package com.lexorahome.Lexora.main.repository;

import com.lexorahome.Lexora.main.picture_service.entity.PictureType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PictureTypeRepository extends JpaRepository<PictureType, UUID> {
}
