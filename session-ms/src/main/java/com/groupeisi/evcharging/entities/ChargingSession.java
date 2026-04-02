package com.groupeisi.evcharging.entities;

import com.groupeisi.evcharging.common.ChargingSessionStatus;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "charging_sessions")
public class ChargingSession {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    private Long vehicleId;

    private Long stationId;

    private Long chargerId;

    @Enumerated(EnumType.STRING)
    private ChargingSessionStatus status = ChargingSessionStatus.STARTED;

    private LocalDateTime startedAt;

    private LocalDateTime endedAt;

    private double energyKwh;

    private long durationMinutes;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(Long vehicleId) {
        this.vehicleId = vehicleId;
    }

    public Long getStationId() {
        return stationId;
    }

    public void setStationId(Long stationId) {
        this.stationId = stationId;
    }

    public Long getChargerId() {
        return chargerId;
    }

    public void setChargerId(Long chargerId) {
        this.chargerId = chargerId;
    }

    public ChargingSessionStatus getStatus() {
        return status;
    }

    public void setStatus(ChargingSessionStatus status) {
        this.status = status;
    }

    public LocalDateTime getStartedAt() {
        return startedAt;
    }

    public void setStartedAt(LocalDateTime startedAt) {
        this.startedAt = startedAt;
    }

    public LocalDateTime getEndedAt() {
        return endedAt;
    }

    public void setEndedAt(LocalDateTime endedAt) {
        this.endedAt = endedAt;
    }

    public double getEnergyKwh() {
        return energyKwh;
    }

    public void setEnergyKwh(double energyKwh) {
        this.energyKwh = energyKwh;
    }

    public long getDurationMinutes() {
        return durationMinutes;
    }

    public void setDurationMinutes(long durationMinutes) {
        this.durationMinutes = durationMinutes;
    }
}

