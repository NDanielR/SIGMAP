package com.ndanielr.daq_api.service;

import java.util.List;

import com.ndanielr.daq_api.model.dto.HourmeterResponseDto;


public interface HourmeterService {
        
    List<HourmeterResponseDto> getAll();

}
