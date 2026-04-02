package com.groupeisi.evcharging.dao;

import com.groupeisi.evcharging.entities.Tariff;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TariffRepository extends JpaRepository<Tariff, Long> {

    Optional<Tariff> findFirstByStationIdOrderByIdDesc(Long stationId);
}

