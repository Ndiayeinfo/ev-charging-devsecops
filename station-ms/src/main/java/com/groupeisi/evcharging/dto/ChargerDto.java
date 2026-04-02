package com.groupeisi.evcharging.dto;

import com.groupeisi.evcharging.entities.enums.ChargerStatus;

public class ChargerDto {

    private Long id;
    private double powerKw;
    private ChargerStatus status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getPowerKw() {
        return powerKw;
    }

    public void setPowerKw(double powerKw) {
        this.powerKw = powerKw;
    }

    public ChargerStatus getStatus() {
        return status;
    }

    public void setStatus(ChargerStatus status) {
        this.status = status;
    }
}

