package com.dasther.ndramirez.simgap_daq.model.dto.cranedto;

import com.dasther.ndramirez.simgap_daq.model.entity.crane.CraneType;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Schema(description = "Información de una grúa registrada")
public class CraneResponseDto {

    @Schema(description = "Identificador de la grúa", example = "1")
    private Long idCrane;

    @Schema(description = "Código de la grúa", example = "RTG01")
    private String name;

    @Schema(description = "Tipo de grúa", example = "RTG")
    private CraneType type;

    @Schema(description = "Estado operativo", example = "true")
    private Boolean isOperational;
}
