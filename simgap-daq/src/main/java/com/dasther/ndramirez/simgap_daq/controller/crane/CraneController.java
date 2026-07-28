package com.dasther.ndramirez.simgap_daq.controller.crane;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.validation.annotation.Validated;
import org.springframework.http.ResponseEntity;

import com.dasther.ndramirez.simgap_daq.model.dto.cranedto.CraneRequestDto;
import com.dasther.ndramirez.simgap_daq.model.dto.cranedto.CraneResponseDto;
import com.dasther.ndramirez.simgap_daq.service.crane.CraneService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@Validated
@RequestMapping("/api/v1/gruas")
public class CraneController {

    private final CraneService craneService;

    public CraneController (CraneService craneService){
        this.craneService = craneService;
    }

    @GetMapping()
    public List <CraneResponseDto> getAll() {
        return craneService.getAll();
    }

    @GetMapping("/nombre/{name}")
    public CraneResponseDto getByName(@PathVariable
            @NotBlank(message = "El nombre de búsqueda es obligatorio")
            String name) {
        return craneService.getByName(name);
    }

    @GetMapping("/buscar/{name}")
    public List<CraneResponseDto> searchByName(
            @PathVariable
            @NotBlank(message = "El nombre de búsqueda es obligatorio")
            String name) {
        return craneService.searchByName(name);
    }

    @PostMapping()
    public CraneResponseDto createCrane(@Valid @RequestBody CraneRequestDto crane) {
        return craneService.createCrane(crane);
    }

    @PutMapping("/{id}")
    public CraneResponseDto updateCrane(@PathVariable @Positive Long id, 
            @Valid @RequestBody CraneRequestDto crane) {

        return craneService.updateCrane(id, crane);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCrane(@PathVariable @Positive Long id) {

        craneService.deleteCrane(id);
        return ResponseEntity.noContent().build();
    }
}
