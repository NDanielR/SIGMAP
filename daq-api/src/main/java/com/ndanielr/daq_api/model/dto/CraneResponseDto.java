package com.ndanielr.daq_api.model.dto;

import com.ndanielr.daq_api.model.entity.Type;

import io.swagger.v3.oas.annotations.media.Schema;

public record CraneResponseDto(

    @Schema(description = "Nombre de la grúa", example = "QC01")
    String name,

    @Schema(description = "Dirección IP del PLC", example = "192.168.0.1")
    String ip,

    @Schema(description = "Rack del PLC", example = "0")
    Integer rack,
  
    @Schema(description = "Slot del PLC", example = "2")
    Integer slot,

    @Schema(description = "Tipo de grúa: QC o RTG", example = "QC")
    Type type

) {
    
}
