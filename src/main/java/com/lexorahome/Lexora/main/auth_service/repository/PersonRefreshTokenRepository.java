package com.lexorahome.Lexora.main.auth_service.repository;


import com.lexorahome.Lexora.main.auth_service.entity.PersonRefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PersonRefreshTokenRepository extends JpaRepository<PersonRefreshToken, UUID> {
}
