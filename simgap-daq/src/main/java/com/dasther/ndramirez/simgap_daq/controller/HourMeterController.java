package com.dasther.ndramirez.simgap_daq.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dasther.ndramirez.simgap_daq.model.dto.HourMeterResponseDto;
import com.dasther.ndramirez.simgap_daq.service.HourMeterService;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;


@RestController
@RequestMapping("api/v1/horometro")
public class HourMeterController {

    private final HourMeterService hourMeterService;

    public HourMeterController (HourMeterService hourMeterService){
        this.hourMeterService = hourMeterService;
    }
    
    @GetMapping
    public List<HourMeterResponseDto> getAllHourMeters() {
        return hourMeterService.getAll();
    }
    
}
