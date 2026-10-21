package com.ndanielr.daq_api.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ndanielr.daq_api.model.dto.CraneResponseDto;
import com.ndanielr.daq_api.model.dto.CreateCraneDto;
import com.ndanielr.daq_api.model.dto.UpdateCraneDto;
import com.ndanielr.daq_api.service.CraneService;

import jakarta.validation.Valid;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;




@RestController
@RequestMapping ("/api/cranes")
@Tag(name = "Grúas", description = "Administración de grúas y su conexión al PLC")
public class CraneController {

    private final CraneService craneService;

    public CraneController(CraneService craneService){
        this.craneService = craneService;
    }

    @GetMapping
    @Operation(summary = "Listar grúas", description = "Devuelve todas las grúas. Si no hay registros, devuelve una lista vacía.")
    @ApiResponse(responseCode = "200", description = "Lista de grúas")
    public ResponseEntity<List<CraneResponseDto>> getAllCrane() {
        return ResponseEntity.ok(craneService.getAll());
    }

    @GetMapping("/id/{id}")
    @Operation(summary = "Consultar grúa por ID")
    @ApiResponse(responseCode = "200", description = "Grúa encontrada")
    @ApiResponse(responseCode = "404", description = "No existe una grúa con ese ID", content = @Content)
    @Parameter(name = "id", description = "ID de la grúa", example = "1")
    public ResponseEntity<CraneResponseDto> getById(@PathVariable @Valid Long id) {
        return ResponseEntity.ok(craneService.getById(id));
    }

    @GetMapping("/name/{name}")
    @Operation(summary = "Buscar grúa por nombre", description = "Busca por coincidencia parcial, sin distinguir mayúsculas. Debe coincidir con una sola grúa; varias coincidencias no se devuelven como lista.")
    @ApiResponse(responseCode = "200", description = "Grúa encontrada")
    @ApiResponse(responseCode = "404", description = "No hay coincidencias", content = @Content)
    @Parameter(name = "name", description = "Nombre o parte del nombre", example = "QC01")
    public ResponseEntity<CraneResponseDto> getById(@PathVariable @Valid String name) {
        return ResponseEntity.ok(craneService.getByName(name));
    }
    

    @PostMapping
    @Operation(summary = "Crear grúa", description = "Registra una grúa. El nombre y la IP no deben estar registrados previamente.")
    @ApiResponse(responseCode = "201", description = "Grúa creada")
    @ApiResponse(responseCode = "400", description = "JSON mal formado o tipos de datos incorrectos", content = @Content)
    @ApiResponse(responseCode = "409", description = "Nombre o IP ya registrados", content = @Content)
    public ResponseEntity<CraneResponseDto> createCrane(@RequestBody @Valid CreateCraneDto dto) {
        CraneResponseDto crane = craneService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(crane);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar grúa", description = "Actualiza name, ip, rack y slot usando el ID de la URL. Enviar todos los campos editables. Conserva el tipo de grúa. El ID del cuerpo no se utiliza.")
    @ApiResponse(responseCode = "200", description = "Grúa actualizada")
    @ApiResponse(responseCode = "400", description = "JSON o ID con formato incorrecto", content = @Content)
    @ApiResponse(responseCode = "404", description = "Grúa no encontrada", content = @Content)
    @ApiResponse(responseCode = "409", description = "El nombre pertenece a otra grúa", content = @Content)
    @Parameter(name = "id", description = "ID de la grúa que se actualizará", example = "1")
    public ResponseEntity<CraneResponseDto> update(@PathVariable Long id,
        @RequestBody @Valid UpdateCraneDto dto) {
        return ResponseEntity.ok(craneService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar grúa", description = "Elimina por ID. Si el ID no existe, también devuelve 204.")
    @ApiResponse(responseCode = "204", description = "Operación completada, sin cuerpo de respuesta", content = @Content)
    @Parameter(name = "id", description = "ID de la grúa que se eliminará", example = "1")
    public ResponseEntity<Void> deleteCrane(@PathVariable Long id){
        craneService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
