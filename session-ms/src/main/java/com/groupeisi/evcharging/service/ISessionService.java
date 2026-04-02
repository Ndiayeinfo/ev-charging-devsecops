package com.groupeisi.evcharging.service;

import com.groupeisi.evcharging.dto.ChargingSessionRequest;
import com.groupeisi.evcharging.dto.ChargingSessionResponse;

import java.util.List;

public interface ISessionService {

    ChargingSessionResponse startSession(ChargingSessionRequest request);

    ChargingSessionResponse stopSession(Long sessionId);

    ChargingSessionResponse getSession(Long sessionId);

    List<ChargingSessionResponse> getUserSessions(Long userId);
}

