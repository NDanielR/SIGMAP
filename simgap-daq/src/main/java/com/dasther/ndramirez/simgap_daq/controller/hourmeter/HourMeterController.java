package com.dasther.ndramirez.simgap_daq.controller.hourmeter;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.validation.annotation.Validated;
import org.springframework.http.ResponseEntity;

import com.dasther.ndramirez.simgap_daq.model.dto.hourmeterdto.HourMeterRequestDto;
import com.dasther.ndramirez.simgap_daq.model.dto.hourmeterdto.HourMeterResponseDto;
import com.dasther.ndramirez.simgap_daq.service.hourmeter.HourMeterService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;




@RestController
@Validated
@RequestMapping("api/v1/horometro")
public class HourMeterController {

    private final HourMeterService hourMeterService;

    public HourMeterController (HourMeterService hourMeterService){
        this.hourMeterService = hourMeterService;
    }
    
    @GetMapping
    public List<HourMeterResponseDto> getAllHourMeters() {
        return hourMeterService.getAll();
    }

    @GetMapping("/nombregrua")
    public List<HourMeterResponseDto> getByName(@RequestParam String name) {
        return hourMeterService.getByCraneName(name);
    }
    
    @PostMapping()
    public HourMeterResponseDto RegisterHourmeters(@Valid @RequestBody HourMeterRequestDto hourDto) {
        return hourMeterService.createHourmeter(hourDto);
    }

    @PutMapping("/{id}")
    public HourMeterResponseDto updateHourmeter(
            @PathVariable @Positive Long id,
            @Valid @RequestBody HourMeterRequestDto hourDto) {

        return hourMeterService.updateHourmeter(id, hourDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteHourmeter(
            @PathVariable @Positive Long id) {

        hourMeterService.deleteHourmeter(id);
        return ResponseEntity.noContent().build();
    }
}
