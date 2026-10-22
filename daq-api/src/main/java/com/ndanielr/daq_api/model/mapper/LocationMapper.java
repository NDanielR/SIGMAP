package com.ndanielr.daq_api.model.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.ndanielr.daq_api.model.dto.LocationResponseDto;
import com.ndanielr.daq_api.model.entity.Location;

@Mapper(componentModel = "spring")
public interface LocationMapper {

    @Mapping (target = "craneName", source = "crane.name")
    LocationResponseDto toDto(Location location);
} 