package com.groupeisi.evcharging.dto;

import java.util.List;

public class StationDto {

    private Long id;
    private String name;
    private String location;
    private List<ChargerDto> chargers;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public List<ChargerDto> getChargers() {
        return chargers;
    }

    public void setChargers(List<ChargerDto> chargers) {
        this.chargers = chargers;
    }
}

