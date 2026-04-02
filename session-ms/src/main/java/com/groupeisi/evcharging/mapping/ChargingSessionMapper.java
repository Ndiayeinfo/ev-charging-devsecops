package com.groupeisi.evcharging.mapping;

import com.groupeisi.evcharging.common.ChargingSessionFinishedEvent;
import com.groupeisi.evcharging.dto.ChargingSessionResponse;
import com.groupeisi.evcharging.entities.ChargingSession;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ChargingSessionMapper {

    ChargingSessionResponse toResponse(ChargingSession session);

    @Mapping(target = "sessionId", source = "id")
    ChargingSessionFinishedEvent toEvent(ChargingSession session);
}

