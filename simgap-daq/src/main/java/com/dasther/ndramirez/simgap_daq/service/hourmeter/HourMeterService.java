package com.dasther.ndramirez.simgap_daq.service.hourmeter;

import java.util.List;

import com.dasther.ndramirez.simgap_daq.model.dto.hourmeterdto.HourMeterRequestDto;
import com.dasther.ndramirez.simgap_daq.model.dto.hourmeterdto.HourMeterResponseDto;

public interface HourMeterService {

    List<HourMeterResponseDto> getAll();

    List<HourMeterResponseDto> getByCraneName(String name);

    HourMeterResponseDto createHourmeter(HourMeterRequestDto hourmeterDto);

    HourMeterResponseDto updateHourmeter(Long id, HourMeterRequestDto hourmeterDto);

    void deleteHourmeter(Long id);
}
