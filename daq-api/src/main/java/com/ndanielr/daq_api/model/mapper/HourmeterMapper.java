package com.ndanielr.daq_api.model.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.ndanielr.daq_api.model.dto.HourmeterResponseDto;
import com.ndanielr.daq_api.model.entity.HourmeterQC;
import com.ndanielr.daq_api.model.entity.HourmeterRTG;

@Mapper(componentModel = "spring")
public interface  HourmeterMapper {

    @Mapping(target = "craneName", source = "crane.name")
    HourmeterResponseDto toDto(HourmeterQC entity);

    @Mapping (target = "craneName", source = "crane.name")
    @Mapping (target = "boomOn", ignore = true)
    HourmeterResponseDto toDto(HourmeterRTG entity);

}
