package com.groupeisi.evcharging.dao;

import com.groupeisi.evcharging.entities.StationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StationRepository extends JpaRepository<StationEntity, Long> {
}

