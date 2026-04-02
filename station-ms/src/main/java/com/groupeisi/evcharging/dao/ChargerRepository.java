package com.groupeisi.evcharging.dao;

import com.groupeisi.evcharging.entities.ChargerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ChargerRepository extends JpaRepository<ChargerEntity, Long> {

    Optional<ChargerEntity> findByIdAndStationId(Long id, Long stationId);
}

