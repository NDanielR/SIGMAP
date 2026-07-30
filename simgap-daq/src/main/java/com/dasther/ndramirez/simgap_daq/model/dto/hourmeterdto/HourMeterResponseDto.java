package com.dasther.ndramirez.simgap_daq.model.dto.hourmeterdto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.Instant;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Schema(description = "Registro de horómetro almacenado")
public class HourMeterResponseDto {

    @Schema(description = "Identificador del registro", example = "1")
    private Long hourMeterId;

    @Schema(description = "Identificador del dispositivo", example = "1")
    private Long deviceId;

    @Schema(description = "Tiempo acumulado de la grúa", example = "200")
    private Long craneOn;

    @Schema(description = "Tiempo acumulado del hoist", example = "20")
    private Long hoistOn;

    @Schema(description = "Tiempo acumulado del trolley", example = "5")
    private Long trolleyOn;

    @Schema(description = "Tiempo acumulado del gantry", example = "3")
    private Long gantryOn;

    @Schema(description = "Tiempo acumulado de overlap", example = "2")
    private Long overlapOn;

    @Schema(description = "Tiempo acumulado del boom", example = "20")
    private Long boomOn;

    @Schema(
            description = "Instante UTC de recepción en el servidor",
            example = "2026-07-28T19:39:37Z",
            format = "date-time"
    )
    private Instant dateReception;
      
}
