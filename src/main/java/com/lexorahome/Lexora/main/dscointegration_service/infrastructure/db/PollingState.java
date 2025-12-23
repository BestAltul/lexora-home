package com.lexorahome.Lexora.main.dscointegration_service.infrastructure.db;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.Instant;

@Entity
@Table(name="polling_state")
public class PollingState {
    @Id
    @Column(name="stream_name",nullable = false)
    private String streamName;

    private Long lastOffset;
    private Instant lastTimeStamp;
    private Instant updatedAt;
}
