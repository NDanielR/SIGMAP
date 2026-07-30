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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

@RestController
@Validated
@RequestMapping("/api/v1/dispositivos")
@Tag(
        name = "Dispositivos",
        description = "Administración de dispositivos IoT asociados a grúas"
)
public class DeviceController {

    private final DeviceService deviceService;

    public DeviceController(DeviceService deviceService) {
        this.deviceService = deviceService;
    }

    @GetMapping
    @Operation(summary = "Listar todos los dispositivos")
    @ApiResponse(responseCode = "200", description = "Listado obtenido")
    public List<DeviceResponseDto> getAll() {
        return deviceService.getAll();
    }

    @GetMapping("/nombre/{name}")
    @Operation(summary = "Buscar un dispositivo por su nombre exacto")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Dispositivo encontrado"),
            @ApiResponse(responseCode = "400", description = "Nombre inválido"),
            @ApiResponse(responseCode = "404", description = "Dispositivo no encontrado")
    })
    public DeviceResponseDto getByName(
            @Parameter(description = "Nombre del dispositivo", example = "DAQ-RTG01")
            @PathVariable
            @NotBlank(message = "El nombre de búsqueda es obligatorio")
            String name) {
        return deviceService.getByName(name);
    }

    @GetMapping("/buscar/{text}")
    @Operation(summary = "Buscar dispositivos que contengan un texto")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Búsqueda realizada"),
            @ApiResponse(responseCode = "400", description = "Texto inválido")
    })
    public List<DeviceResponseDto> searchByName(
            @Parameter(description = "Texto contenido en el nombre", example = "RTG")
            @PathVariable
            @NotBlank(message = "El texto de búsqueda es obligatorio")
            String text) {
        return deviceService.searchByName(text);
    }

    @PostMapping
    @Operation(summary = "Crear un dispositivo")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Dispositivo creado"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos"),
            @ApiResponse(responseCode = "404", description = "Grúa no encontrada"),
            @ApiResponse(
                    responseCode = "409",
                    description = "Nombre, IP o MAC duplicados"
            )
    })
    public DeviceResponseDto createDevice(
            @Valid @RequestBody DeviceRequestDto deviceDto) {
        return deviceService.createDevice(deviceDto);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar un dispositivo")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Dispositivo actualizado"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos"),
            @ApiResponse(responseCode = "404", description = "Registro no encontrado"),
            @ApiResponse(
                    responseCode = "409",
                    description = "Nombre, IP o MAC duplicados"
            )
    })
    public DeviceResponseDto updateDevice(
            @Parameter(description = "ID del dispositivo", example = "1")
            @PathVariable @Positive Long id,
            @Valid @RequestBody DeviceRequestDto deviceDto) {
        return deviceService.updateDevice(id, deviceDto);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar un dispositivo")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Dispositivo eliminado"),
            @ApiResponse(responseCode = "400", description = "ID inválido"),
            @ApiResponse(responseCode = "404", description = "Dispositivo no encontrado"),
            @ApiResponse(
                    responseCode = "409",
                    description = "El dispositivo tiene horómetros relacionados"
            )
    })
    public ResponseEntity<Void> deleteDevice(
            @Parameter(description = "ID del dispositivo", example = "1")
            @PathVariable @Positive Long id) {
        deviceService.deleteDevice(id);
        return ResponseEntity.noContent().build();
    }
}
