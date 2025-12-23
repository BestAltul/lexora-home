package com.lexorahome.Lexora.main.dscointegration_service.infrastructure.db;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PollingStateRepository extends JpaRepository<PollingState,String> {

}
