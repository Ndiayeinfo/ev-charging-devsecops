package com.groupeisi.evcharging.service.impl;

import com.groupeisi.evcharging.dao.ChargerRepository;
import com.groupeisi.evcharging.dao.StationRepository;
import com.groupeisi.evcharging.dto.ChargerCreateRequest;
import com.groupeisi.evcharging.dto.StationCreateRequest;
import com.groupeisi.evcharging.dto.StationDto;
import com.groupeisi.evcharging.entities.ChargerEntity;
import com.groupeisi.evcharging.entities.StationEntity;
import com.groupeisi.evcharging.entities.enums.ChargerStatus;
import com.groupeisi.evcharging.mapping.StationMapper;
import com.groupeisi.evcharging.service.IStationService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class StationService implements IStationService {

    private final StationRepository stationRepository;
    private final ChargerRepository chargerRepository;
    private final StationMapper stationMapper;

    public StationService(StationRepository stationRepository,
                          ChargerRepository chargerRepository,
                          StationMapper stationMapper) {
        this.stationRepository = stationRepository;
        this.chargerRepository = chargerRepository;
        this.stationMapper = stationMapper;
    }

    @Override
    @Transactional(readOnly = true)
    public List<StationDto> getStations() {
        return stationMapper.toDtoList(stationRepository.findAll());
    }

    @Override
    @Transactional(readOnly = true)
    public StationDto getStation(Long id) {
        StationEntity station = stationRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Station not found: " + id));
        return stationMapper.toDto(station);
    }

    @Override
    public StationDto createStation(StationCreateRequest request) {
        StationEntity entity = stationMapper.fromCreateRequest(request);
        StationEntity saved = stationRepository.save(entity);
        return stationMapper.toDto(saved);
    }

    @Override
    public StationDto addCharger(Long stationId, ChargerCreateRequest request) {
        StationEntity station = stationRepository.findById(stationId)
                .orElseThrow(() -> new IllegalArgumentException("Station not found: " + stationId));

        ChargerEntity charger = new ChargerEntity();
        charger.setStation(station);
        charger.setPowerKw(request.getPowerKw());
        charger.setStatus(ChargerStatus.AVAILABLE);

        chargerRepository.save(charger);

        // recharger la station pour inclure la nouvelle borne dans la collection
        StationEntity reloaded = stationRepository.findById(stationId)
                .orElseThrow(() -> new IllegalArgumentException("Station not found after charger creation: " + stationId));

        return stationMapper.toDto(reloaded);
    }

    @Override
    public void lockCharger(Long stationId, Long chargerId) {
        ChargerEntity charger = chargerRepository.findByIdAndStationId(chargerId, stationId)
                .orElseThrow(() -> new IllegalArgumentException("Charger not found for station"));
        if (charger.getStatus() != ChargerStatus.AVAILABLE) {
            throw new IllegalStateException("Charger not available");
        }
        charger.setStatus(ChargerStatus.IN_USE);
    }

    @Override
    public void unlockCharger(Long stationId, Long chargerId) {
        ChargerEntity charger = chargerRepository.findByIdAndStationId(chargerId, stationId)
                .orElseThrow(() -> new IllegalArgumentException("Charger not found for station"));
        charger.setStatus(ChargerStatus.AVAILABLE);
    }
}

