package com.lexorahome.Lexora.main.auth_service.repository;


import com.lexorahome.Lexora.main.auth_service.entity.PersonRefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface RefreshRequestRepository extends JpaRepository<PersonRefreshToken, UUID> {
    @Query("SELECT r FROM PersonRefreshToken r WHERE r.person.email= :email")
    List<PersonRefreshToken> findAllByPersonEmail(@Param("email") String refreshToken);
}
