package com.groupeisi.evcharging.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class TariffRequest {

    @NotNull
    private Long stationId;

    @Min(0)
    private double pricePerKwh;

    @Min(0)
    private double pricePerMinute;

    @Min(0)
    private double idlePenaltyPerMinute;

    public Long getStationId() {
        return stationId;
    }

    public void setStationId(Long stationId) {
        this.stationId = stationId;
    }

    public double getPricePerKwh() {
        return pricePerKwh;
    }

    public void setPricePerKwh(double pricePerKwh) {
        this.pricePerKwh = pricePerKwh;
    }

    public double getPricePerMinute() {
        return pricePerMinute;
    }

    public void setPricePerMinute(double pricePerMinute) {
        this.pricePerMinute = pricePerMinute;
    }

    public double getIdlePenaltyPerMinute() {
        return idlePenaltyPerMinute;
    }

    public void setIdlePenaltyPerMinute(double idlePenaltyPerMinute) {
        this.idlePenaltyPerMinute = idlePenaltyPerMinute;
    }
}

