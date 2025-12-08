package com.lexorahome.Lexora.main.picture_service.repository;

import com.lexorahome.Lexora.main.picture_service.entity.UserPictureStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserPictureStatusRepository extends JpaRepository<UserPictureStatus, UUID> {

}
