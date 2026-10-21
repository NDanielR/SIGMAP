package com.ndanielr.daq_api.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ndanielr.daq_api.model.dto.HourmeterResponseDto;
import com.ndanielr.daq_api.service.HourmeterService;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;


@RestController 
@RequestMapping ("/api/hourmeters")
public class HourmeterController {

    private final HourmeterService hourmeterService;

    public HourmeterController(HourmeterService hourmeterService) {
        this.hourmeterService = hourmeterService;
    }

    @GetMapping
    public ResponseEntity<List<HourmeterResponseDto>> getAllHourmeters() {
        return ResponseEntity.ok(hourmeterService.getAll());
    }
    
    
}
