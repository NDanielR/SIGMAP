package com.ndanielr.daq_api.service.implement;

import java.util.List;
import java.util.stream.Stream;

import org.springframework.stereotype.Service;

import com.ndanielr.daq_api.model.dto.HourmeterResponseDto;
import com.ndanielr.daq_api.model.mapper.HourmeterMapper;
import com.ndanielr.daq_api.repository.HourmeterQCRepository;
import com.ndanielr.daq_api.repository.HourmeterRTGRepository;
import com.ndanielr.daq_api.service.HourmeterService;

@Service 
public class HourmeterServiceImplem implements HourmeterService{

    private final HourmeterQCRepository hourmeterQCRepository;
    private final HourmeterRTGRepository hourmeterRTGRepository;
    private final HourmeterMapper hourmeterMapper;
    
    public HourmeterServiceImplem(
        HourmeterQCRepository hourmeterQCRepository,
        HourmeterRTGRepository hourmeterRTGRepository,
        HourmeterMapper hourmeterMapper) {
            this.hourmeterQCRepository = hourmeterQCRepository;
            this.hourmeterRTGRepository = hourmeterRTGRepository;
            this.hourmeterMapper = hourmeterMapper;
    }

    @Override
    public List<HourmeterResponseDto> getAll() {
        var listQc = hourmeterQCRepository
                .findAll()
                .stream()
                .map(hourmeterMapper::toDto)
                .toList();
        
        var listRtg = hourmeterRTGRepository
                .findAll()
                .stream()
                .map(hourmeterMapper::toDto)
                .toList();

        return Stream.concat(
            listQc.stream(), 
            listRtg.stream())
            .toList();
    }
    
}
