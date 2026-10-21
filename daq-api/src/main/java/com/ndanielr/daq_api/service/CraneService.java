package com.ndanielr.daq_api.service;

import java.util.List;

import com.ndanielr.daq_api.model.dto.CraneResponseDto;
import com.ndanielr.daq_api.model.dto.CreateCraneDto;
import com.ndanielr.daq_api.model.dto.UpdateCraneDto;

public interface CraneService {

    List<CraneResponseDto> getAll();

    CraneResponseDto getById(Long id);

    CraneResponseDto getByName(String name);

    CraneResponseDto create(CreateCraneDto dto);

    CraneResponseDto update(Long id, UpdateCraneDto dto);

    void delete(Long id);

}
