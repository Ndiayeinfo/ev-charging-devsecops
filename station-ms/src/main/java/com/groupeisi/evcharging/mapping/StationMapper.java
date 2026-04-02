package com.groupeisi.evcharging.mapping;

import com.groupeisi.evcharging.dto.StationCreateRequest;
import com.groupeisi.evcharging.dto.StationDto;
import com.groupeisi.evcharging.entities.StationEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface StationMapper {

    StationDto toDto(StationEntity station);

    List<StationDto> toDtoList(List<StationEntity> stations);

    StationEntity fromCreateRequest(StationCreateRequest request);
}

