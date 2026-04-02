package com.groupeisi.evcharging.service.impl;

import com.groupeisi.evcharging.common.ChargingSessionFinishedEvent;
import com.groupeisi.evcharging.common.ChargingSessionStatus;
import com.groupeisi.evcharging.dao.ChargingSessionRepository;
import com.groupeisi.evcharging.dto.ChargingSessionRequest;
import com.groupeisi.evcharging.dto.ChargingSessionResponse;
import com.groupeisi.evcharging.entities.ChargingSession;
import com.groupeisi.evcharging.mapping.ChargingSessionMapper;
import com.groupeisi.evcharging.service.ISessionService;
import com.groupeisi.evcharging.session.StationClient;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class SessionService implements ISessionService {

    private final ChargingSessionRepository sessionRepository;
    private final StationClient stationClient;
    private final ChargingSessionMapper sessionMapper;
    private final KafkaTemplate<String, ChargingSessionFinishedEvent> kafkaTemplate;

    private static final String SESSION_TOPIC = "charging-sessions-events";

    public SessionService(ChargingSessionRepository sessionRepository,
                          StationClient stationClient,
                          ChargingSessionMapper sessionMapper,
                          KafkaTemplate<String, ChargingSessionFinishedEvent> kafkaTemplate) {
        this.sessionRepository = sessionRepository;
        this.stationClient = stationClient;
        this.sessionMapper = sessionMapper;
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public ChargingSessionResponse startSession(ChargingSessionRequest request) {
        boolean locked = stationClient.lockCharger(request.getStationId(), request.getChargerId());
        if (!locked) {
            throw new IllegalStateException("Unable to lock charger");
        }

        ChargingSession session = new ChargingSession();
        session.setUserId(request.getUserId());
        session.setVehicleId(request.getVehicleId());
        session.setStationId(request.getStationId());
        session.setChargerId(request.getChargerId());
        session.setStatus(ChargingSessionStatus.STARTED);
        session.setStartedAt(LocalDateTime.now());

        return sessionMapper.toResponse(sessionRepository.save(session));
    }

    @Override
    public ChargingSessionResponse stopSession(Long sessionId) {
        ChargingSession session = sessionRepository.findById(sessionId)
                .orElseThrow(() -> new IllegalArgumentException("Session not found: " + sessionId));

        if (session.getStatus() == ChargingSessionStatus.FINISHED) {
            return sessionMapper.toResponse(session);
        }

        session.setEndedAt(LocalDateTime.now());
        long durationMinutes = Duration.between(session.getStartedAt(), session.getEndedAt()).toMinutes();
        session.setDurationMinutes(durationMinutes);
        session.setEnergyKwh(durationMinutes * 0.2);
        session.setStatus(ChargingSessionStatus.FINISHED);

        stationClient.unlockCharger(session.getStationId(), session.getChargerId());

        ChargingSession saved = sessionRepository.save(session);

        ChargingSessionFinishedEvent event = sessionMapper.toEvent(saved);
        kafkaTemplate.send(SESSION_TOPIC, String.valueOf(saved.getId()), event);

        return sessionMapper.toResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public ChargingSessionResponse getSession(Long sessionId) {
        ChargingSession session = sessionRepository.findById(sessionId)
                .orElseThrow(() -> new IllegalArgumentException("Session not found: " + sessionId));
        return sessionMapper.toResponse(session);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ChargingSessionResponse> getUserSessions(Long userId) {
        return sessionRepository.findByUserId(userId).stream()
                .map(sessionMapper::toResponse)
                .toList();
    }
}

