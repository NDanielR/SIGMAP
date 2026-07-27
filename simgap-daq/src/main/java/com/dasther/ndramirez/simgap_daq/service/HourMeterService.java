package com.dasther.ndramirez.simgap_daq.service;

import java.util.List;

import com.dasther.ndramirez.simgap_daq.model.dto.HourMeterRequestDto;
import com.dasther.ndramirez.simgap_daq.model.dto.HourMeterResponseDto;

public interface HourMeterService {

    List<HourMeterResponseDto> getAll();

    HourMeterRequestDto createHourmeter (HourMeterRequestDto hourmeterDto);
}
