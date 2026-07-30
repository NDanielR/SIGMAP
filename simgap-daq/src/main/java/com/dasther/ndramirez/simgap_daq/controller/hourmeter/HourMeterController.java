package com.dasther.ndramirez.simgap_daq.controller.hourmeter;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dasther.ndramirez.simgap_daq.model.dto.hourmeterdto.HourMeterRequestDto;
import com.dasther.ndramirez.simgap_daq.model.dto.hourmeterdto.HourMeterResponseDto;
import com.dasther.ndramirez.simgap_daq.service.hourmeter.HourMeterService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;

@RestController
@Validated
@RequestMapping("/api/v1/horometro")
@Tag(
        name = "Horómetros",
        description = "Lecturas de horómetro enviadas por dispositivos IoT"
)
public class HourMeterController {

    private final HourMeterService hourMeterService;

    public HourMeterController(HourMeterService hourMeterService) {
        this.hourMeterService = hourMeterService;
    }

    @GetMapping
    @Operation(summary = "Listar todos los registros de horómetro")
    @ApiResponse(responseCode = "200", description = "Listado obtenido")
    public List<HourMeterResponseDto> getAllHourMeters() {
        return hourMeterService.getAll();
    }

    @GetMapping("/nombregrua")
    @Operation(summary = "Buscar horómetros por nombre de grúa")
    @ApiResponse(responseCode = "200", description = "Búsqueda realizada")
    public List<HourMeterResponseDto> getByName(
            @Parameter(description = "Código de la grúa", example = "RTG01")
            @RequestParam String name) {
        return hourMeterService.getByCraneName(name);
    }

    @PostMapping
    @Operation(summary = "Registrar una lectura de horómetro")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lectura registrada"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos"),
            @ApiResponse(responseCode = "404", description = "Dispositivo no encontrado")
    })
    public HourMeterResponseDto registerHourmeters(
            @Valid @RequestBody HourMeterRequestDto hourDto) {
        return hourMeterService.createHourmeter(hourDto);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar una lectura de horómetro")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lectura actualizada"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos"),
            @ApiResponse(responseCode = "404", description = "Registro no encontrado")
    })
    public HourMeterResponseDto updateHourmeter(
            @Parameter(description = "ID del registro", example = "1")
            @PathVariable @Positive Long id,
            @Valid @RequestBody HourMeterRequestDto hourDto) {
        return hourMeterService.updateHourmeter(id, hourDto);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar una lectura de horómetro")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Lectura eliminada"),
            @ApiResponse(responseCode = "400", description = "ID inválido"),
            @ApiResponse(responseCode = "404", description = "Registro no encontrado")
    })
    public ResponseEntity<Void> deleteHourmeter(
            @Parameter(description = "ID del registro", example = "1")
            @PathVariable @Positive Long id) {
        hourMeterService.deleteHourmeter(id);
        return ResponseEntity.noContent().build();
    }
}
