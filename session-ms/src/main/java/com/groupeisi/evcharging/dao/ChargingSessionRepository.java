package com.groupeisi.evcharging.dao;

import com.groupeisi.evcharging.common.ChargingSessionStatus;
import com.groupeisi.evcharging.entities.ChargingSession;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChargingSessionRepository extends JpaRepository<ChargingSession, Long> {

    List<ChargingSession> findByUserId(Long userId);

    List<ChargingSession> findByStatus(ChargingSessionStatus status);
}

