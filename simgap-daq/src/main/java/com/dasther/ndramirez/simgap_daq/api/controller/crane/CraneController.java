package com.dasther.ndramirez.simgap_daq.api.controller.crane;

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

import com.dasther.ndramirez.simgap_daq.api.model.dto.cranedto.CraneRequestDto;
import com.dasther.ndramirez.simgap_daq.api.model.dto.cranedto.CraneResponseDto;
import com.dasther.ndramirez.simgap_daq.api.service.crane.CraneService;

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
@RequestMapping("/api/v1/gruas")
@Tag(name = "Grúas", description = "Administración del catálogo de grúas")
public class CraneController {

    private final CraneService craneService;

    public CraneController(CraneService craneService) {
        this.craneService = craneService;
    }

    @GetMapping
    @Operation(summary = "Listar todas las grúas")
    @ApiResponse(responseCode = "200", description = "Listado obtenido")
    public List<CraneResponseDto> getAll() {
        return craneService.getAll();
    }

    @GetMapping("/nombre/{name}")
    @Operation(summary = "Buscar una grúa por su nombre exacto")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Grúa encontrada"),
            @ApiResponse(responseCode = "400", description = "Nombre inválido"),
            @ApiResponse(responseCode = "404", description = "Grúa no encontrada")
    })
    public CraneResponseDto getByName(
            @Parameter(description = "Código de la grúa", example = "RTG01")
            @PathVariable
            @NotBlank(message = "El nombre de búsqueda es obligatorio")
            String name) {
        return craneService.getByName(name);
    }

    @GetMapping("/buscar/{name}")
    @Operation(summary = "Buscar grúas que contengan un texto en su nombre")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Búsqueda realizada"),
            @ApiResponse(responseCode = "400", description = "Texto inválido")
    })
    public List<CraneResponseDto> searchByName(
            @Parameter(description = "Texto contenido en el nombre", example = "RTG")
            @PathVariable
            @NotBlank(message = "El nombre de búsqueda es obligatorio")
            String name) {
        return craneService.searchByName(name);
    }

    @PostMapping
    @Operation(summary = "Crear una grúa")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Grúa creada"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos"),
            @ApiResponse(responseCode = "409", description = "Nombre duplicado")
    })
    public CraneResponseDto createCrane(
            @Valid @RequestBody CraneRequestDto crane) {
        return craneService.createCrane(crane);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar una grúa")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Grúa actualizada"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos"),
            @ApiResponse(responseCode = "404", description = "Grúa no encontrada"),
            @ApiResponse(responseCode = "409", description = "Nombre duplicado")
    })
    public CraneResponseDto updateCrane(
            @Parameter(description = "ID de la grúa", example = "1")
            @PathVariable @Positive Long id,
            @Valid @RequestBody CraneRequestDto crane) {
        return craneService.updateCrane(id, crane);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar una grúa")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Grúa eliminada"),
            @ApiResponse(responseCode = "400", description = "ID inválido"),
            @ApiResponse(responseCode = "404", description = "Grúa no encontrada"),
            @ApiResponse(
                    responseCode = "409",
                    description = "La grúa tiene registros relacionados"
            )
    })
    public ResponseEntity<Void> deleteCrane(
            @Parameter(description = "ID de la grúa", example = "1")
            @PathVariable @Positive Long id) {
        craneService.deleteCrane(id);
        return ResponseEntity.noContent().build();
    }
}
