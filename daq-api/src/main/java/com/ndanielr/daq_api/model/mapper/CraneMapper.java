package com.ndanielr.daq_api.model.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.ndanielr.daq_api.model.dto.CraneResponseDto;
import com.ndanielr.daq_api.model.dto.CreateCraneDto;
import com.ndanielr.daq_api.model.dto.UpdateCraneDto;
import com.ndanielr.daq_api.model.entity.Crane;

@Mapper(componentModel = "spring")
public interface CraneMapper {

    @Mapping(target = "id", ignore = true)
    Crane toEntity(CreateCraneDto dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "type", ignore = true)
    Crane toEntity(UpdateCraneDto dto);

    CraneResponseDto toDto(Crane entity);
}
