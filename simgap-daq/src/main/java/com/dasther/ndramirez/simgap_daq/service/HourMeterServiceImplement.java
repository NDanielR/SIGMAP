package com.dasther.ndramirez.simgap_daq.service;

import java.time.Instant;
import java.util.List;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dasther.ndramirez.simgap_daq.model.dto.HourMeterRequestDto;
import com.dasther.ndramirez.simgap_daq.model.dto.HourMeterResponseDto;
import com.dasther.ndramirez.simgap_daq.model.entity.Device;
import com.dasther.ndramirez.simgap_daq.model.entity.HourMeters;
import com.dasther.ndramirez.simgap_daq.repository.DeviceRepository;
import com.dasther.ndramirez.simgap_daq.repository.HourMeterRepository;

@Service
public class HourMeterServiceImplement implements HourMeterService{

    private final HourMeterRepository hourMeterRepository;
    private final DeviceRepository deviceRepository;

    public HourMeterServiceImplement(
            HourMeterRepository hourMeterRepository,
            DeviceRepository deviceRepository) {
        this.hourMeterRepository = hourMeterRepository;
        this.deviceRepository = deviceRepository;
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
    @Transactional
    public HourMeterResponseDto createHourmeter(HourMeterRequestDto hourmeterDto) {
        var device = deviceRepository.findById(hourmeterDto.getDeviceId())
                .orElseThrow(() -> new EntityNotFoundException(
                        "No existe un dispositivo con ID "
                                + hourmeterDto.getDeviceId()
                ));

        var hourMeter = toEntity(hourmeterDto, device);
        var savedHourMeter = hourMeterRepository.save(hourMeter);

        return toDto(savedHourMeter);
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

    private HourMeters toEntity(HourMeterRequestDto dto, Device device) {
        var hourMeter = new HourMeters();
        hourMeter.setDevice(device);
        hourMeter.setTimeCraneOn(dto.getCraneOn());
        hourMeter.setTimeHoistOn(dto.getHoistOn());
        hourMeter.setTimeTrolleyOn(dto.getTrolleyOn());
        hourMeter.setTimeGantryOn(dto.getGantryOn());
        hourMeter.setTimeOverlapOn(dto.getOverlapOn());
        hourMeter.setTimeBoomOn(dto.getBoomOn());
        hourMeter.setDateReport(dto.getDateReport());
        hourMeter.setDateReception(Instant.now());
        return hourMeter;
    }
    
}
