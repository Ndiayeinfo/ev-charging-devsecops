package com.groupeisi.evcharging.entities;

import com.groupeisi.evcharging.entities.enums.ChargerStatus;
import jakarta.persistence.*;

@Entity
@Table(name = "chargers")
public class ChargerEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "station_id")
    private StationEntity station;

    private double powerKw;

    @Enumerated(EnumType.STRING)
    private ChargerStatus status = ChargerStatus.AVAILABLE;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public StationEntity getStation() {
        return station;
    }

    public void setStation(StationEntity station) {
        this.station = station;
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

