package com.groupeisi.evcharging.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "tariffs")
public class Tariff {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long stationId;

    private double pricePerKwh;

    private double pricePerMinute;

    private double idlePenaltyPerMinute;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

