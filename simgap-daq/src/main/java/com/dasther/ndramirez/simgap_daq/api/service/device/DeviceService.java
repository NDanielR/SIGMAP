package com.dasther.ndramirez.simgap_daq.api.service.device;

import java.util.List;

import com.dasther.ndramirez.simgap_daq.api.model.dto.devicedto.DeviceRequestDto;
import com.dasther.ndramirez.simgap_daq.api.model.dto.devicedto.DeviceResponseDto;

public interface DeviceService {

    List<DeviceResponseDto> getAll();

    DeviceResponseDto getByName(String name);

    List<DeviceResponseDto> searchByName(String text);

    DeviceResponseDto createDevice(DeviceRequestDto deviceDto);

    DeviceResponseDto updateDevice(Long id, DeviceRequestDto deviceDto);

    void deleteDevice(Long id);
}
