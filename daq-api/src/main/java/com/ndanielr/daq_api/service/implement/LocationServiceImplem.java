package com.ndanielr.daq_api.service.implement;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ndanielr.daq_api.model.dto.LocationResponseDto;
import com.ndanielr.daq_api.model.mapper.LocationMapper;
import com.ndanielr.daq_api.repository.LocationRepository;
import com.ndanielr.daq_api.service.LocationService;

@Service 
public class LocationServiceImplem implements LocationService{

    private final LocationRepository locationRepository;
    private final LocationMapper locationMapper;

    
    public LocationServiceImplem(
        LocationRepository locationRepository,
        LocationMapper locationMapper) {
            
            this.locationRepository = locationRepository;
            this.locationMapper = locationMapper;
    }

    @Override
    public List<LocationResponseDto> getAll() {
        var list = locationRepository
            .findAll()
            .stream()
            .map(locationMapper::toDto)
            .toList();
        
        return list;
    }
    
}
