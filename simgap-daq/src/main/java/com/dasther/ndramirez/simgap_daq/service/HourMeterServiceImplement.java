package com.dasther.ndramirez.simgap_daq.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dasther.ndramirez.simgap_daq.model.dto.HourMeterRequestDto;
import com.dasther.ndramirez.simgap_daq.model.dto.HourMeterResponseDto;
import com.dasther.ndramirez.simgap_daq.model.entity.HourMeters;
import com.dasther.ndramirez.simgap_daq.repository.HourMeterRepository;

@Service
public class HourMeterServiceImplement implements HourMeterService{

    private final HourMeterRepository hourMeterRepository;

    public HourMeterServiceImplement(HourMeterRepository hourMeterRepository) {
        this.hourMeterRepository = hourMeterRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<HourMeterResponseDto> getAll() {
        return hourMeterRepository.findAll()
                .stream()
                .map(this::toDto)
                .toList();
    }

    @Override
    public HourMeterRequestDto createHourmeter(HourMeterRequestDto hourmeterDto) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'createHourmeter'");
    }

    private HourMeterResponseDto toDto(HourMeters hourMeter) {

        var dto = new HourMeterResponseDto();
        dto.setHourMeterId(hourMeter.getIdHourmeter());
        dto.setDeviceId(hourMeter.getDevice().getIdDevice());
        dto.setCraneOn(hourMeter.getTimeCraneOn());
        dto.setHoistOn(hourMeter.getTimeHoistOn());
        dto.setTrolleyOn(hourMeter.getTimeTrolleyOn());
        dto.setGantryOn(hourMeter.getTimeGantryOn());
        dto.setOverlapOn(hourMeter.getTimeOverlapOn());
        dto.setBoomOn(hourMeter.getTimeBoomOn());
        dto.setDateReception(hourMeter.getDateReception());
        return dto;
    }
    
}
