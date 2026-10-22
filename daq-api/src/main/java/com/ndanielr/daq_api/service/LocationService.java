package com.ndanielr.daq_api.service;

import java.util.List;

import com.ndanielr.daq_api.model.dto.LocationResponseDto;

public interface LocationService {
    
    List<LocationResponseDto> getAll();
}
