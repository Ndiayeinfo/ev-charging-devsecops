package com.groupeisi.evcharging.service;

import com.groupeisi.evcharging.dto.ChargerCreateRequest;
import com.groupeisi.evcharging.dto.StationCreateRequest;
import com.groupeisi.evcharging.dto.StationDto;

import java.util.List;

public interface IStationService {

    List<StationDto> getStations();

    StationDto getStation(Long id);

    StationDto createStation(StationCreateRequest request);

    StationDto addCharger(Long stationId, ChargerCreateRequest request);

    void lockCharger(Long stationId, Long chargerId);

    void unlockCharger(Long stationId, Long chargerId);
}

