package com.ndanielr.daq_api.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ndanielr.daq_api.model.dto.LocationResponseDto;
import com.ndanielr.daq_api.service.LocationService;

@RestController 
@RequestMapping ("/api/locations")
public class LocationController {
    
    private final LocationService locationService;

    public LocationController(LocationService locationService) {
        this.locationService = locationService;
    }

    @GetMapping
    public ResponseEntity<List<LocationResponseDto>> getAllLocations() {
        return ResponseEntity.ok(locationService.getAll());
    }
}
