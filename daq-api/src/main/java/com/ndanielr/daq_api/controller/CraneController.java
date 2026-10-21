package com.ndanielr.daq_api.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ndanielr.daq_api.model.dto.CraneResponseDto;
import com.ndanielr.daq_api.model.dto.CreateCraneDto;
import com.ndanielr.daq_api.model.dto.UpdateCraneDto;
import com.ndanielr.daq_api.service.CraneService;

import jakarta.validation.Valid;

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
public class CraneController {

    private final CraneService craneService;

    public CraneController(CraneService craneService){
        this.craneService = craneService;
    }

    @GetMapping
    public ResponseEntity<List<CraneResponseDto>> getAllCrane() {
        return ResponseEntity.ok(craneService.getAll());
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<CraneResponseDto> getById(@PathVariable @Valid Long id) {
        return ResponseEntity.ok(craneService.getById(id));
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<CraneResponseDto> getById(@PathVariable @Valid String name) {
        return ResponseEntity.ok(craneService.getByName(name));
    }
    

    @PostMapping
    public ResponseEntity<CraneResponseDto> createCrane(@RequestBody @Valid CreateCraneDto dto) {
        CraneResponseDto crane = craneService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(crane);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CraneResponseDto> update(@PathVariable Long id,
        @RequestBody @Valid UpdateCraneDto dto) {
        return ResponseEntity.ok(craneService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCrane(@PathVariable Long id){
        craneService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
