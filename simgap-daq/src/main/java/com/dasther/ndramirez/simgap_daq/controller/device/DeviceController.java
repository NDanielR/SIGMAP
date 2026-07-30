package com.dasther.ndramirez.simgap_daq.controller.device;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dasther.ndramirez.simgap_daq.model.dto.devicedto.DeviceRequestDto;
import com.dasther.ndramirez.simgap_daq.model.dto.devicedto.DeviceResponseDto;
import com.dasther.ndramirez.simgap_daq.service.device.DeviceService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

@RestController
@Validated
@RequestMapping("/api/v1/dispositivos")
public class DeviceController {

    private final DeviceService deviceService;

    public DeviceController(DeviceService deviceService) {
        this.deviceService = deviceService;
    }

    @GetMapping
    public List<DeviceResponseDto> getAll() {
        return deviceService.getAll();
    }

    @GetMapping("/nombre/{name}")
    public DeviceResponseDto getByName(
            @PathVariable
            @NotBlank(message = "El nombre de búsqueda es obligatorio")
            String name) {

        return deviceService.getByName(name);
    }

    @GetMapping("/buscar/{text}")
    public List<DeviceResponseDto> searchByName(
            @PathVariable
            @NotBlank(message = "El texto de búsqueda es obligatorio")
            String text) {

        return deviceService.searchByName(text);
    }

    @PostMapping
    public DeviceResponseDto createDevice(
            @Valid @RequestBody DeviceRequestDto deviceDto) {

        return deviceService.createDevice(deviceDto);
    }

    @PutMapping("/{id}")
    public DeviceResponseDto updateDevice(
            @PathVariable @Positive Long id,
            @Valid @RequestBody DeviceRequestDto deviceDto) {

        return deviceService.updateDevice(id, deviceDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDevice(
            @PathVariable @Positive Long id) {

        deviceService.deleteDevice(id);
        return ResponseEntity.noContent().build();
    }
}
