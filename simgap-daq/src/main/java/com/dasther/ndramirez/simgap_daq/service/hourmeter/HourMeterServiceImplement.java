package com.dasther.ndramirez.simgap_daq.service.hourmeter;

import java.time.Instant;
import java.util.List;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dasther.ndramirez.simgap_daq.model.dto.hourmeterdto.HourMeterRequestDto;
import com.dasther.ndramirez.simgap_daq.model.dto.hourmeterdto.HourMeterResponseDto;
import com.dasther.ndramirez.simgap_daq.model.entity.device.Device;
import com.dasther.ndramirez.simgap_daq.model.entity.hourmeter.HourMeters;
import com.dasther.ndramirez.simgap_daq.repository.device.DeviceRepository;
import com.dasther.ndramirez.simgap_daq.repository.hourmeter.HourMeterRepository;

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
        var device = getDeviceById(hourmeterDto.getDeviceId());

        var hourMeter = toEntity(hourmeterDto, device);
        var savedHourMeter = hourMeterRepository.save(hourMeter);

        return toDto(savedHourMeter);
    }

    @Override
    public List<HourMeterResponseDto> getByCraneName(String name) {

        return hourMeterRepository.findByDevice_Crane_NameIgnoreCaseOrderByDateReportDesc(name)
                .stream()
                .map(this::toDto)
                .toList();
    }

    @Override
    @Transactional
    public HourMeterResponseDto updateHourmeter(Long id, HourMeterRequestDto hourmeterDto) {

        var hourMeter = hourMeterRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "No existe un registro de horómetro con ID " + id
                ));

        var device = getDeviceById(hourmeterDto.getDeviceId());

        updateEntity(hourMeter, hourmeterDto, device);
        var updatedHourMeter = hourMeterRepository.save(hourMeter);

        return toDto(updatedHourMeter);
    }

    @Override
    @Transactional
    public void deleteHourmeter(Long id) {
        var hourMeter = hourMeterRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "No existe un registro de horómetro con ID " + id
                ));

        hourMeterRepository.delete(hourMeter);
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

    private void updateEntity(
            HourMeters hourMeter,
            HourMeterRequestDto dto,
            Device device) {

        hourMeter.setDevice(device);
        hourMeter.setTimeCraneOn(dto.getCraneOn());
        hourMeter.setTimeHoistOn(dto.getHoistOn());
        hourMeter.setTimeTrolleyOn(dto.getTrolleyOn());
        hourMeter.setTimeGantryOn(dto.getGantryOn());
        hourMeter.setTimeOverlapOn(dto.getOverlapOn());
        hourMeter.setTimeBoomOn(dto.getBoomOn());
        hourMeter.setDateReport(dto.getDateReport());
    }

    private Device getDeviceById(Long deviceId) {
        return deviceRepository.findById(deviceId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "No existe un dispositivo con ID " + deviceId
                ));
    }
}
