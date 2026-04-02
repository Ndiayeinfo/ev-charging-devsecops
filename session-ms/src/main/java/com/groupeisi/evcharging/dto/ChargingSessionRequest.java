package com.groupeisi.evcharging.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class ChargingSessionRequest {

    @NotNull
    private Long userId;

    @NotNull
    private Long vehicleId;

    @NotNull
    private Long stationId;

    @NotNull
    private Long chargerId;

    @Min(0)
    private long expectedDurationMinutes;

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

    public long getExpectedDurationMinutes() {
        return expectedDurationMinutes;
    }

    public void setExpectedDurationMinutes(long expectedDurationMinutes) {
        this.expectedDurationMinutes = expectedDurationMinutes;
    }
}

