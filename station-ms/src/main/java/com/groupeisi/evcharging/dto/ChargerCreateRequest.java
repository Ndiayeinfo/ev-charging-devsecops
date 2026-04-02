package com.groupeisi.evcharging.dto;

import jakarta.validation.constraints.Min;

public class ChargerCreateRequest {

    @Min(1)
    private double powerKw;

    public double getPowerKw() {
        return powerKw;
    }

    public void setPowerKw(double powerKw) {
        this.powerKw = powerKw;
    }
}

