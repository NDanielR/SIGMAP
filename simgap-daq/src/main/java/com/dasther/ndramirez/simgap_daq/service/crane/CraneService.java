package com.dasther.ndramirez.simgap_daq.service.crane;

import java.util.List;

import com.dasther.ndramirez.simgap_daq.model.dto.cranedto.CraneRequestDto;
import com.dasther.ndramirez.simgap_daq.model.dto.cranedto.CraneResponseDto;

public interface CraneService {

    List<CraneResponseDto> getAll();

    CraneResponseDto getByName(String name);

    List<CraneResponseDto> searchByName(String text);

    CraneResponseDto createCrane(CraneRequestDto craneDto);
    
}
